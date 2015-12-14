package de.haw.rsa.receivesecuredfile.dataaccesslayer;

/**
 * Created by JanDennis on 14.12.2015.
 */
public class SendSecureFile {
    private byte[] encryptedSecretKey;
    private byte[] signature;
    private byte[] algorithmicParameters;
    private byte[] encryptedData;

    public SendSecureFile(byte[] encryptedSecretKey, byte[] signature, byte[] algorithmicParameters, byte[] encryptedData) {
        this.encryptedSecretKey = encryptedSecretKey;
        this.signature = signature;
        this.algorithmicParameters = algorithmicParameters;
        this.encryptedData = encryptedData;
    }

    public byte[] getEncryptedSecretKey() {
        return encryptedSecretKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    public byte[] getAlgorithmicParameters() {
        return algorithmicParameters;
    }

    public byte[] getEncryptedData() {
        return encryptedData;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SendSecureFile{");
        sb.append("encryptedSecretKey=");
        if (encryptedSecretKey == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < encryptedSecretKey.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(encryptedSecretKey[i]);
            sb.append(']');
        }
        sb.append(", \nsignature=");
        if (signature == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < signature.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(signature[i]);
            sb.append(']');
        }
        sb.append(", \nalgorithmicParameters=");
        if (algorithmicParameters == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < algorithmicParameters.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(algorithmicParameters[i]);
            sb.append(']');
        }
        sb.append(", \nencryptedData=");
        if (encryptedData == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < encryptedData.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(encryptedData[i]);
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }
}
