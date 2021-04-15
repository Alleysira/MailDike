package cn.edu.buaa.crypto.algebra.genparams;

import org.bouncycastle.crypto.KeyGenerationParameters;

import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;

/**
 * Created by Weiran Liu on 2016/11/20.
 * <p>
 * Pairing secret key delegation parameter.
 */
public class PairingKeyDelegationParameter extends KeyGenerationParameters {
    private PairingKeySerParameter publicKeyParameter;
    private PairingKeySerParameter secretKeyParameter;

    public PairingKeyDelegationParameter(PairingKeySerParameter publicKeyParameter, PairingKeySerParameter secretKeyParameter) {
        super(null, PairingParametersGenerationParameter.STENGTH);
        this.publicKeyParameter = publicKeyParameter;
        this.secretKeyParameter = secretKeyParameter;
    }

    public PairingKeySerParameter getPublicKeyParameter() {
        return this.publicKeyParameter;
    }

    public PairingKeySerParameter getSecretKeyParameter() {
        return this.secretKeyParameter;
    }
}