package com.example.trytolearn.encryption.CPABEAPP;

import org.bouncycastle.crypto.InvalidCipherTextException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cn.edu.buaa.crypto.access.parser.ParserUtils;
import cn.edu.buaa.crypto.access.parser.PolicySyntaxException;
import cn.edu.buaa.crypto.algebra.serparams.PairingCipherSerParameter;
import cn.edu.buaa.crypto.algebra.serparams.PairingKeyEncapsulationSerPair;
import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerPair;
import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;
import cn.edu.buaa.crypto.encryption.abe.cpabe.bsw07.CPABEBSW07Engine;
import cn.edu.buaa.crypto.encryption.abe.cpabe.bsw07.serparams.CPABEBSW07HeaderSerParameter;
import cn.edu.buaa.crypto.encryption.abe.cpabe.bsw07.serparams.CPABEBSW07MasterSecretKeySerParameter;
import cn.edu.buaa.crypto.encryption.abe.cpabe.bsw07.serparams.CPABEBSW07PublicKeySerParameter;
import cn.edu.buaa.crypto.encryption.abe.cpabe.bsw07.serparams.CPABEBSW07SecretKeySerParameter;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class CPABE {
    public static CPABEBSW07Engine engine = new CPABEBSW07Engine();
    private static final String default_path_header = "benchmarks/encryption/cpabe/header/";
    private static final String default_path_publickey = "benchmarks/encryption/cpabe/publickey/";
    private static final String default_path_masterkey = "benchmarks/encryption/cpabe/masterkey/";
    private static final String default_path_secretkey = "benchmarks/encryption/cpabe/secretkey/";
    private static final String default_path_ciphertext = "benchmarks/encryption/cpabe/ciphertext/";
    private static final String default_path_message = "benchmarks/encryption/cpabe/message/";


    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] readbyte(String file) {
        In input = new In(file);
        String[] str = input.readAll().split("\n");
        byte[] vals = new byte[str.length];
        int j = 0;
        for (int i = 0; i < str.length; i++) {
            vals[j++] = (byte) Integer.valueOf(str[i]).intValue();
        }
        return vals;
    }

    public static byte[][] readbytes(String file_rhos, String file_c) {
        In input_rhos = new In(file_rhos);
        In input_c = new In(file_c);
        String[] str_rho = input_rhos.readAll().split("\n");
        String[] str_c = input_c.readAll().split("\n");
        byte[][] vals = new byte[str_rho.length][64];
        byte[] tmp = new byte[64];
        int j = 0;
        int k = 0;
        for (int i = 0; i < str_c.length; i++) {
            if (str_c[i].equals("next")) {
                System.arraycopy(tmp, 0, vals[j++], 0, tmp.length);
                k = 0;
                java.util.Arrays.fill(tmp, (byte) 0);
            } else {
                tmp[k++] = (byte) Integer.valueOf(str_c[i]).intValue();
            }
        }
        return vals;
    }

    public static String[] readstr(String file) {
        In input = new In(file);
        String[] str = input.readAll().split("\n");
        return str;
    }

    public void output_publickey(PairingKeySerParameter publickey, String out_pubkey_p,
                                 String out_pubkey_g, String out_pubkey_h, String out_pubkey_f, String out_pubkey_eggAlpha) {
        CPABEBSW07PublicKeySerParameter Publickey = (CPABEBSW07PublicKeySerParameter) publickey;

        Out out_publickey_P = new Out(out_pubkey_p);
        out_publickey_P.print(Publickey.getParameters());

        Out out_publickey_g = new Out(out_pubkey_g);
        byte[] publickey_g = Publickey.getG().toBytes();
        for (int i = 0; i < publickey_g.length; i++) {
            out_publickey_g.println(publickey_g[i]);
        }

        Out out_publickey_h = new Out(out_pubkey_h);
        byte[] publickey_h = Publickey.getH().toBytes();
        for (int i = 0; i < publickey_h.length; i++) {
            out_publickey_h.println(publickey_h[i]);
        }

        Out out_publickey_f = new Out(out_pubkey_f);
        byte[] publickey_f = Publickey.getF().toBytes();
        for (int i = 0; i < publickey_f.length; i++) {
            out_publickey_f.println(publickey_f[i]);
        }

        Out out_publickey_eggAlpha = new Out(out_pubkey_eggAlpha);
        byte[] publickey_eggAlpha = Publickey.getEggAlpha().toBytes();
        for (int i = 0; i < publickey_eggAlpha.length; i++) {
            out_publickey_eggAlpha.println(publickey_eggAlpha[i]);
        }
    }

    public void output_masterkey(PairingKeySerParameter masterkey, String out_masterkey_p,
                                 String out_masterkey_gAlpha, String out_masterkey_Beta) {
        CPABEBSW07MasterSecretKeySerParameter Masterkey = (CPABEBSW07MasterSecretKeySerParameter) masterkey;

        Out out_masterkey_P = new Out(out_masterkey_p);
        out_masterkey_P.print(Masterkey.getParameters());

        Out out_masterkey_galpha = new Out(out_masterkey_gAlpha);
        byte[] masterkey_galpha = Masterkey.getGAlpha().toBytes();
        for (int i = 0; i < masterkey_galpha.length; i++) {
            out_masterkey_galpha.println(masterkey_galpha[i]);
        }

        Out out_masterkey_beta = new Out(out_masterkey_Beta);
        byte[] masterkey_beta = Masterkey.getBeta().toBytes();
        for (int i = 0; i < masterkey_beta.length; i++) {
            out_masterkey_beta.println(masterkey_beta[i]);
        }
    }

    public void output_Secretkey(PairingKeySerParameter secretkey, String[] attributes, String out_secretkey_Parameters, String out_secretkey_d,
                                 String out_secretkey_attributes, String out_secretkey_d1s, String out_secretkey_d2s) {

        CPABEBSW07SecretKeySerParameter Secretkey = (CPABEBSW07SecretKeySerParameter) secretkey;

        Out out_secretkey_P = new Out(out_secretkey_Parameters);
        out_secretkey_P.print(Secretkey.getParameters());

        Out out_secretkey_D = new Out(out_secretkey_d);
        byte[] secretkeyD = Secretkey.getD().toBytes();
        for (int i = 0; i < secretkeyD.length; i++) {
            out_secretkey_D.println(secretkeyD[i]);
        }

        Out out_secretkey_Attributes = new Out(out_secretkey_attributes);
        for (int i = 0; i < attributes.length; i++) {
            out_secretkey_Attributes.println(attributes[i]);
        }

        Out out_secretkey_D1s = new Out(out_secretkey_d1s);
        for (int i = 0; i < attributes.length; i++) {
            byte[] secretkey_D1s = Secretkey.getD1sAt(attributes[i]).toBytes();
            for (int j = 0; j < secretkey_D1s.length; j++) {
                out_secretkey_D1s.println(secretkey_D1s[j]);
                secretkey_D1s[j] = 0;
            }
            out_secretkey_D1s.println("next");
        }

        Out out_secretkey_D2s = new Out(out_secretkey_d2s);
        for (int i = 0; i < attributes.length; i++) {
            byte[] secretkey_D2s = Secretkey.getD2sAt(attributes[i]).toBytes();
            for (int j = 0; j < secretkey_D2s.length; j++) {
                out_secretkey_D2s.println(secretkey_D2s[j]);
                secretkey_D2s[j] = 0;
            }
            out_secretkey_D2s.println("next");
        }
    }

    public void output_header(PairingCipherSerParameter header, String[] rhos, String out_header_Parameters, String out_header_c,
                              String out_header_crhos, String out_header_c1s, String out_header_c2s) {

        CPABEBSW07HeaderSerParameter Header = (CPABEBSW07HeaderSerParameter) header;

        Out out_header_P = new Out(out_header_Parameters);
        out_header_P.print(Header.getParameters());

        Out out_header_C = new Out(out_header_c);
        byte[] headerc = Header.getC().toBytes();
        for (int i = 0; i < headerc.length; i++) {
            out_header_C.println(headerc[i]);
        }
        Out out_header_Crhos = new Out(out_header_crhos);
        for (int i = 0; i < rhos.length; i++) {
            out_header_Crhos.println(rhos[i]);
        }

        Out out_header_C1s = new Out(out_header_c1s);
        for (int i = 0; i < rhos.length; i++) {
            byte[] headerc1s = Header.getC1sAt(rhos[i]).toBytes();
            for (int j = 0; j < headerc1s.length; j++) {
                out_header_C1s.println(headerc1s[j]);
                headerc1s[j] = 0;
            }
            out_header_C1s.println("next");
        }

        Out out_header_C2s = new Out(out_header_c2s);
        for (int i = 0; i < rhos.length; i++) {
            byte[] headerc2s = Header.getC2sAt(rhos[i]).toBytes();
            // System.out.println("headerc2s_length: "+headerc2s.length);
            for (int j = 0; j < headerc2s.length; j++) {
                out_header_C2s.println(headerc2s[j]);
                headerc2s[j] = 0;
            }
            out_header_C2s.println("next");
        }
    }


    public static Map<String, Element> fTM(String file_c, String file_rhos, PairingParameters header_p) {
        Map<String, Element> map = new HashMap<String, Element>();
        String[] rho = readstr(file_rhos);
        byte[][] c = readbytes(file_rhos, file_c);
        Pairing pairing = PairingFactory.getPairing(header_p);
        for (int i = 0; i < rho.length; i++) {
            map.put(rho[i], pairing.getG1().newElementFromBytes(c[i]).getImmutable());
        }
        return map;
    }


    public static PairingCipherSerParameter getheader(String file_header_parameters, String file_header_c,
                                                      String file_header_c1s, String file_header_c2s, String file_header_rhos) {
        PairingParameters header_Parameters = PairingFactory.getPairingParameters(file_header_parameters);
        byte[] c = readbyte(file_header_c);
        Pairing pairing = PairingFactory.getPairing(header_Parameters);
        Element header_C = pairing.getG1().newElementFromBytes(c);
        Map<String, Element> header_C1s = fTM(file_header_c1s, file_header_rhos, header_Parameters);
        Map<String, Element> header_C2s = fTM(file_header_c2s, file_header_rhos, header_Parameters);
        CPABEBSW07HeaderSerParameter anHeader = new CPABEBSW07HeaderSerParameter(header_Parameters, header_C, header_C1s, header_C2s);
        PairingCipherSerParameter header = (PairingCipherSerParameter) anHeader;
        return header;
    }

    public PairingKeySerParameter getpublickey(String out_pubkey_p, String out_pubkey_g,
                                               String out_pubkey_h, String out_pubkey_f, String out_pubkey_eggAlpha) {
        PairingParameters publickey_Parameters = PairingFactory.getPairingParameters(out_pubkey_p);
        byte[] g = readbyte(out_pubkey_g);
        byte[] h = readbyte(out_pubkey_h);
        byte[] f = readbyte(out_pubkey_f);
        byte[] eggAlpha = readbyte(out_pubkey_eggAlpha);
        Pairing pairing = PairingFactory.getPairing(publickey_Parameters);
        Element publickey_g = pairing.getG1().newElementFromBytes(g);
        Element publickey_h = pairing.getG1().newElementFromBytes(h);
        Element publickey_f = pairing.getG1().newElementFromBytes(f);
        Element publickey_eggAlpha = pairing.getG1().newElementFromBytes(eggAlpha);
        CPABEBSW07PublicKeySerParameter anpublickey = new CPABEBSW07PublicKeySerParameter(publickey_Parameters, publickey_g, publickey_h, publickey_f, publickey_eggAlpha);
        PairingKeySerParameter publickey = (PairingKeySerParameter) anpublickey;
        return publickey;
    }

    public PairingKeySerParameter getmasterkey(String out_masterkey_p, String out_masterkey_gAlpha, String out_masterkey_Beta) {
        PairingParameters masterkey_Parameters = PairingFactory.getPairingParameters(out_masterkey_p);
        byte[] gAlpha = readbyte(out_masterkey_gAlpha);
        byte[] Beta = readbyte(out_masterkey_Beta);
        Pairing pairing = PairingFactory.getPairing(masterkey_Parameters);
        Element masterkey_gAlpha = pairing.getG1().newElementFromBytes(gAlpha);
        Element masterkey_Beta = pairing.getG1().newElementFromBytes(Beta);
        PairingKeySerParameter masterkey = (PairingKeySerParameter) new CPABEBSW07MasterSecretKeySerParameter(masterkey_Parameters, masterkey_gAlpha, masterkey_Beta);
        return masterkey;
    }


    public PairingKeySerParameter getsecretkey(String file_secretkey_parameters, String file_secretkey_d,
                                               String file_secretkey_d1s, String file_secretkey_d2s, String file_secretkey_attributes) {
        PairingParameters secretkey_Parameters = PairingFactory.getPairingParameters(file_secretkey_parameters);
        byte[] d = readbyte(file_secretkey_d);
        Pairing pairing = PairingFactory.getPairing(secretkey_Parameters);
        Element secretkey_D = pairing.getG1().newElementFromBytes(d);
        Map<String, Element> secretkey_D1s = fTM(file_secretkey_d1s, file_secretkey_attributes, secretkey_Parameters);
        Map<String, Element> secretkey_D2s = fTM(file_secretkey_d2s, file_secretkey_attributes, secretkey_Parameters);
        CPABEBSW07SecretKeySerParameter anSecretkey = new CPABEBSW07SecretKeySerParameter(secretkey_Parameters, secretkey_D, secretkey_D1s, secretkey_D2s);
        PairingKeySerParameter secretkey = (PairingKeySerParameter) anSecretkey;
        return secretkey;
    }

    public void Encryption(PairingKeySerParameter publicKey, String Policy,
                           String message, String out_header_Parameters, String out_header_c,
                           String out_header_crhos, String out_header_c1s, String out_header_c2s, String out_Ciphertext) {
        int[][] accessPolicy = new int[0][];//set access policy associating with the ciphertext, given by string
        try {
            accessPolicy = ParserUtils.GenerateAccessPolicy(Policy);
        } catch (PolicySyntaxException e) {
            System.out.println("GenerateAccessPolicy Failed!");
        }
        String[] rhos = new String[0];//set rhos associating with the ciphertext, given by string array
        try {
            rhos = ParserUtils.GenerateRhos(Policy);
        } catch (PolicySyntaxException e) {
            System.out.println("GenerateRhos Failed!");
        }
        PairingKeyEncapsulationSerPair encapsulationPair = engine.encapsulation(publicKey, accessPolicy, rhos);//encapsulation
        byte[] sessionKey = encapsulationPair.getSessionKey();//get SessionKey
        PairingCipherSerParameter header = encapsulationPair.getHeader();//get header

        output_header(header, rhos, out_header_Parameters, out_header_c,
                out_header_crhos, out_header_c1s, out_header_c2s);

        MD5 md_5 = new MD5();//hash session key
        byte[] Key = md_5.md5(sessionKey);
        String key_128 = bytesToHexString(Key);
        //System.out.println(key_128);

        AES aes = new AES();//encrypt by AES
        String ciphertext = null;
        try {
            ciphertext = aes.aesEnc(message, key_128);
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncoding!");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithm!");
        } catch (NoSuchPaddingException e) {
            System.out.println("NoSuchPadding!");
        } catch (InvalidKeyException e) {
            System.out.println("InvalidKey!");
        } catch (IllegalBlockSizeException e) {
            System.out.println("IllegalBlockSize!");
        } catch (BadPaddingException e) {
            System.out.println("BadPadding!");
        } catch (InvalidAlgorithmParameterException e) {
            System.out.println("InvalidAlgorithmParameter!");
        }

        Out out_ciphertext = new Out(out_Ciphertext);//output ciphertext
        out_ciphertext.print(ciphertext);
    }

    public String Deryption(PairingKeySerParameter publicKey, String attribute,
                            PairingKeySerParameter secretKey, PairingCipherSerParameter header,
                            String file_ciphertext) {
        In in_ciphertext = new In(file_ciphertext);
        String ciphertext = in_ciphertext.readAll();

        int[][] attributesPolicy = new int[0][];
        try {
            attributesPolicy = ParserUtils.GenerateAccessPolicy(attribute);
        } catch (PolicySyntaxException e) {
            System.out.println("PolicySyntax Error!");
        }
        String[] attributesrhos = new String[0];
        try {
            attributesrhos = ParserUtils.GenerateRhos(attribute);
        } catch (PolicySyntaxException e) {
            System.out.println("GenerateRhos Failed!");
        }
        byte[] anSessionKey = {0};
        try {
            anSessionKey = engine.decapsulation(publicKey, secretKey, attributesPolicy, attributesrhos, header);//decapsulation
        } catch (InvalidCipherTextException e) {
            System.out.println("InvalidCipherText!");
        }

        MD5 md_5 = new MD5();//hash session key
        byte[] Key = md_5.md5(anSessionKey);
        String key_128 = bytesToHexString(Key);
        //System.out.println(key_128);

        AES aes = new AES();//decrypt by AES
        String message = null;
        try {
            message = aes.aesDec(ciphertext, key_128);
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncoding!");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithm!");
        } catch (NoSuchPaddingException e) {
            System.out.println("NoSuchPadding!");
        } catch (InvalidKeyException e) {
            System.out.println("InvalidKey!");
        } catch (IllegalBlockSizeException e) {
            System.out.println("IllegalBlockSize!");
        } catch (BadPaddingException e) {
            System.out.println("BadPadding!");
        } catch (InvalidAlgorithmParameterException e) {
            System.out.println("InvalidAlgorithmParameter!");
        }
        return message;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CPABE cpabe = new CPABE();
        PairingParameters pairingParameters = PairingFactory.getPairingParameters("params/a_80_256.properties");
        PairingKeySerPair keyPair = engine.setup(pairingParameters, 50);//setup

        PairingKeySerParameter publicKey = keyPair.getPublic();//get publicKey
        cpabe.output_publickey(publicKey, default_path_publickey + "pubkey_p", default_path_publickey + "pubkey_g",
                default_path_publickey + "pubkey_h", default_path_publickey + "pubkey_f", default_path_publickey + "pubkey_eggalpha");

        PairingKeySerParameter masterKey = keyPair.getPrivate();//get masterKey
        cpabe.output_masterkey(masterKey, default_path_masterkey + "masterkey_p",
                default_path_masterkey + "masterkey_gAlpha", default_path_masterkey + "masterkey_Beta,");

        String[] attributes = new String[]{"Beijing", "HD", "BUAA"};//set attributes

        String attribute = "Beijing and HD and BUAA";
        PairingKeySerParameter secretKey = engine.keyGen(publicKey, masterKey, attributes);//generate secretKey
        cpabe.output_Secretkey(secretKey, attributes, default_path_secretkey + "secretkey_Parameters",
                default_path_secretkey + "secretkey_D", default_path_secretkey + "secretkey_attributes",
                default_path_secretkey + "secretkey_D1s", default_path_secretkey + "secretkey_D2s");

        String Policy = "Beijing and HD and BUAA ";
        String message = "hello BUAA";//message
        cpabe.Encryption(publicKey, Policy, message,
                default_path_header + "header_Parameters", default_path_header + "header_C",
                default_path_header + "header_Crhos", default_path_header + "header_C1s",
                default_path_header + "header_C2s", default_path_ciphertext + "ciphertext1");

        PairingKeySerParameter anpublicKey = cpabe.getpublickey(default_path_publickey + "pubkey_p", default_path_publickey + "pubkey_g",
                default_path_publickey + "pubkey_h", default_path_publickey + "pubkey_f", default_path_publickey + "pubkey_eggalpha");

        PairingKeySerParameter ansecretKey = cpabe.getsecretkey(default_path_secretkey + "secretkey_Parameters",
                default_path_secretkey + "secretkey_D", default_path_secretkey + "secretkey_D1s",
                default_path_secretkey + "secretkey_D2s", default_path_secretkey + "secretkey_attributes");

        PairingCipherSerParameter header = getheader(default_path_header + "header_Parameters",
                default_path_header + "header_C", default_path_header + "header_C1s",
                default_path_header + "header_C2s", default_path_header + "header_Crhos");

        System.out.println(cpabe.Deryption(anpublicKey, attribute, ansecretKey, header, default_path_ciphertext + "ciphertext1"));
    }
}
