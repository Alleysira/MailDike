package cn.edu.buaa.crypto.algebra.generators;

import org.bouncycastle.crypto.KeyGenerationParameters;

import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerPair;

/**
 * Created by Weiran Liu on 2016/11/9.
 * <p>
 * Asymmetric serializable key pair generator.
 */
public interface PairingKeyPairGenerator {
    /**
     * intialise the key pair generator.
     *
     * @param param the parameters the key pair is to be initialised with.
     */
    void init(KeyGenerationParameters param);

    /**
     * return an AsymmetricCipherKeyPair containing the generated keys.
     *
     * @return an AsymmetricCipherKeyPair containing the generated keys.
     */
    PairingKeySerPair generateKeyPair();
}
