package cn.edu.buaa.crypto.encryption.abe.cpabe.hw14.serparams;

import java.util.Map;

import cn.edu.buaa.crypto.encryption.abe.cpabe.rw13.serparams.CPABERW13SecretKeySerParameter;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.PairingParameters;

/**
 * Created by Weiran Liu on 2017/1/1.
 * <p>
 * Hohenberger-Waters-14 OO-CP-ABE secret key parameter.
 */
public class CPABEHW14SecretKeySerParameter extends CPABERW13SecretKeySerParameter {
    public CPABEHW14SecretKeySerParameter(
            PairingParameters pairingParameters, Element K0, Element K1,
            Map<String, Element> K2s, Map<String, Element> K3s) {
        super(pairingParameters, K0, K1, K2s, K3s);
    }
}
