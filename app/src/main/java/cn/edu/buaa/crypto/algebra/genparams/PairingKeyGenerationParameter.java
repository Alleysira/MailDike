package cn.edu.buaa.crypto.algebra.genparams;

import org.bouncycastle.crypto.KeyGenerationParameters;

import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;

/**
 * Created by Weiran Liu on 2016/11/20.
 * <p>
 * Pairing secret key generation parameter.
 */
public class PairingKeyGenerationParameter extends KeyGenerationParameters {
    private PairingKeySerParameter masterSecretKeyParameter;
    private PairingKeySerParameter publicKeyParameter;

    public PairingKeyGenerationParameter(PairingKeySerParameter publicKeyParameter, PairingKeySerParameter masterSecretKeyParameter) {
        super(null, PairingParametersGenerationParameter.STENGTH);

        this.masterSecretKeyParameter = masterSecretKeyParameter;
        this.publicKeyParameter = publicKeyParameter;
    }

    public PairingKeySerParameter getMasterSecretKeyParameter() {
        return this.masterSecretKeyParameter;
    }

    public PairingKeySerParameter getPublicKeyParameter() {
        return this.publicKeyParameter;
    }
}

