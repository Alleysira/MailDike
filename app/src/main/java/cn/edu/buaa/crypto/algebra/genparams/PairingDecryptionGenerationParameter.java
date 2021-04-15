package cn.edu.buaa.crypto.algebra.genparams;

import org.bouncycastle.crypto.CipherParameters;

import cn.edu.buaa.crypto.algebra.serparams.PairingCipherSerParameter;
import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;

/**
 * Created by Weiran Liu on 2016/11/20.
 * <p>
 * Pairing decryption generation parameter
 */
public abstract class PairingDecryptionGenerationParameter implements CipherParameters {
    private PairingKeySerParameter publicKeyParameter;
    private PairingKeySerParameter secretKeyParameter;
    private PairingCipherSerParameter ciphertextParameter;

    public PairingDecryptionGenerationParameter(
            PairingKeySerParameter publicKeyParameter, PairingKeySerParameter secretKeyParameter,
            PairingCipherSerParameter ciphertextParameter) {
        this.publicKeyParameter = publicKeyParameter;
        this.secretKeyParameter = secretKeyParameter;
        this.ciphertextParameter = ciphertextParameter;
    }

    public PairingKeySerParameter getPublicKeyParameter() {
        return this.publicKeyParameter;
    }

    public PairingKeySerParameter getSecretKeyParameter() {
        return this.secretKeyParameter;
    }

    public PairingCipherSerParameter getCiphertextParameter() {
        return this.ciphertextParameter;
    }
}
