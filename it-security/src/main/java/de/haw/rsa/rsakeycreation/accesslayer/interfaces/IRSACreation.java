package de.haw.rsa.rsakeycreation.accesslayer.interfaces;

import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;

/**
 * Created by Patrick Steinhauer on 09.12.2015.
 */
public interface IRSACreation {

    /**
     *
     * @param publicKeyOwner
     * @return
     */
    PublicKey createPublicKey(String publicKeyOwner);

    /**
     *
     * @param privateKeyOwner
     * @return
     */
    PrivateKey createPrivateKey(String privateKeyOwner);
}
