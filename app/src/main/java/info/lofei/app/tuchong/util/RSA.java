package info.lofei.app.tuchong.util;

import java.math.BigInteger;

import info.lofei.app.tuchong.vendor.TuChongApi;

/**
 * RSA encrypt.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-02 21:03
 */
public class RSA {

    private int mExponent; // public exponent
    private BigInteger mModules; // modulus

    // boolean (can encrypt?)
    public boolean mCanEncrypt;

    private BigInteger Zero = new BigInteger("0");

    public RSA() {
        this.mModules = new BigInteger(TuChongApi.PUBLIC_MODULE, 16);
        this.mExponent = Integer.parseInt(TuChongApi.PUBLIC_EXPONENT, 16);
        // adjust a few flags.
        mCanEncrypt = (mModules != null && mModules != Zero && mExponent != 0);
    }

    public int getBlockSize() {
        return (mModules.bitLength() + 7) / 8;
    }

    public BigInteger doPublic(BigInteger x) {
        if (this.mCanEncrypt) {
            return x.modPow(new BigInteger(this.mExponent + ""), this.mModules);
        }

        return Zero;
    }

    public String encrypt(String text) {

        BigInteger m = new BigInteger(this.pkcs1pad2(text.getBytes(), this.getBlockSize()));

        if (m.equals(Zero)) {
            return null;
        }

        BigInteger c = this.doPublic(m);

        if (c.equals(Zero)) {
            return null;
        }

        String result = c.toString(16);

        if ((result.length() & 1) == 0) {
            return result;
        }

        return "0" + result;
    }

    private byte[] pkcs1pad2(byte[] data, int n) {
        byte[] bytes = new byte[n];

        int i = data.length - 1;

        while (i >= 0 && n > 11) {
            bytes[--n] = data[i--];
        }

        bytes[--n] = 0;

        while (n > 2) {
            bytes[--n] = 0x01;
        }

        bytes[--n] = 0x2;
        bytes[--n] = 0;

        return bytes;
    }

    private byte[] pkcs1unpad2(BigInteger src, int n) {
        byte[] bytes = src.toByteArray();
        byte[] out;
        int i = 0;

        while (i < bytes.length && bytes[i] == 0) {
            ++i;
        }

        if (bytes.length - i != n - 1 || bytes[i] > 2) {
            return null;
        }

        ++i;

        while (bytes[i] != 0) {
            if (++i >= bytes.length) {
                return null;
            }
        }

        out = new byte[(bytes.length - i) + 1];
        int p = 0;

        while (++i < bytes.length) {
            out[p++] = (bytes[i]);
        }

        return out;
    }
}