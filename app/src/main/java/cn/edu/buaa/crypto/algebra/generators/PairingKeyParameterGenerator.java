package cn.edu.buaa.crypto.algebra.generators;

import org.bouncycastle.crypto.KeyGenerationParameters;

import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;

/**
 * Created by Weiran Liu on 2016/11/9.
 * <p>
 * Pairing-based serializable key parameters generator
 */
public interface PairingKeyParameterGenerator {

    void init(KeyGenerationParameters keyGenerationParameters);

    PairingKeySerParameter generateKey();
}
