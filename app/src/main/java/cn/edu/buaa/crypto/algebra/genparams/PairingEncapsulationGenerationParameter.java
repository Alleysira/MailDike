package cn.edu.buaa.crypto.algebra.genparams;

import org.bouncycastle.crypto.CipherParameters;

import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;

/**
 * Created by Administrator on 2016/11/20.
 * <p>
 * Pairing key encapsulation generation parameter.
 */
public abstract class PairingEncapsulationGenerationParameter implements CipherParameters {
    private PairingKeySerParameter publicKeyParameter;

    public PairingEncapsulationGenerationParameter(PairingKeySerParameter publicKeyParameter) {
        this.publicKeyParameter = publicKeyParameter;
    }

    public PairingKeySerParameter getPublicKeyParameter() {
        return this.publicKeyParameter;
    }
}
