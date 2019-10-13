package com.xjf.util.encryption;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Calendar;
import java.util.Random;

import com.xjf.util.Pair;

import java.util.Map.Entry;  
public class RSA {
    public RSA() {
    }
    public static KeyPair generateKey() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.genKeyPair();
            //PublicKey pbkey = kp.getPublic();
            //PrivateKey prkey = kp.getPrivate();
            return kp;
        } catch (Exception e) {
        }
		return null;
    }
    public static Pair<BigInteger,BigInteger> generateKey(BigInteger d) {
        try {
    		BigInteger p = BigInteger.probablePrime(520, new Random(Calendar.getInstance().getTimeInMillis()));
    		BigInteger q = BigInteger.probablePrime(520, new Random(Calendar.getInstance().getTimeInMillis()));
    		BigInteger n = p.multiply(q);
    		BigInteger fn = p.subtract(BigInteger.ONE);
    		fn = fn.multiply(q.subtract(BigInteger.ONE));
    		Entry<BigInteger,BigInteger> e = Euclid.expandEuclid(d, fn);
    		System.out.println(e.getKey().multiply(d));
    		System.out.println(e.getValue().multiply(fn));
    		//BigInteger e = key.getKey();
    		Pair<BigInteger,BigInteger> key = new Pair<BigInteger, BigInteger>(e.getKey(), n);
            return key;
        } catch (Exception e) {
        }
		return null;
    }
    private BigInteger e;
    private BigInteger n;
    private BigInteger d;
    public RSA(BigInteger e, BigInteger n, BigInteger d) {
    	this.e = e;
    	this.n = n;
    	this.d = d;
    }
    public static byte[] encrypt(BigInteger e, BigInteger n) throws Exception {  
        String s = "中";
        System.out.println("c= " + s);
        // 获取公钥及参数e,n  
        System.out.println("e= " + e + ",n= " + n);  
        // 获取明文m  
        int len1 = (n.bitLength()-1)/8 ;
        int len2 = (n.bitLength()+7)/8 ;
        byte ptext[] = s.getBytes("UTF-8");
        byte rtext[] = new byte[ptext.length+ptext.length/len2+1];
        byte[] tmp1 = new byte[len1];
        byte[] tmp2 = new byte[len2];
        BigInteger m;
        for(int i=0 ; i*len1<ptext.length ; i++){
        	System.arraycopy(ptext, i*len1, tmp1, 0, len1);
	        m = new BigInteger(tmp1);  
	        // 计算密文
	        tmp2 = m.modPow(e, n).toByteArray();
        	System.arraycopy(ptext, 0, tmp2, i*len2, len2);
        }
        return rtext;
    }  
    public static void decrypt(BigInteger d, BigInteger n, byte[] ctext) throws Exception {  
        System.out.println("d= " + d + ",n= " + n);  
        int len1 = (n.bitLength()-1)/8 ;
        int len2 = (n.bitLength()+7)/8 ;
        char ptext[] = new char[ctext.length];
        byte[] tmp2 = new byte[len2];
        byte[] tmp1 ;
        BigInteger m;
        for(int i=0 ; i*len2<ptext.length ; i++){
        	System.arraycopy(ctext, i*len2, tmp2, 0, len2);
	        m = new BigInteger(tmp2);
	        m = m.modPow(d, n);  
	        tmp1 = m.toByteArray();
        	System.arraycopy(tmp1, i*len2, ptext, 0, len1);
        }
        String str = new String(ptext);  
        // 显示解密结果  
        System.out.println("m= " + str);  
    }  
    public static void main(String args[]) {  
        try {
        	//BigInteger d = new BigInteger("314159");
        	BigInteger d = new BigInteger("65537");
        	Pair<BigInteger,BigInteger> p = generateKey(d);
        	BigInteger e = p.getKey();
        	BigInteger n = p.getValue();//*/
        	/*KeyPair kp = generateKey();
        	RSAPublicKey pbkey = (RSAPublicKey) kp.getPublic();
        	RSAPrivateKey prkey = (RSAPrivateKey) kp.getPrivate();
        	BigInteger e = pbkey.getPublicExponent();
        	BigInteger d = prkey.getPrivateExponent();
        	BigInteger n = prkey.getModulus();//*/
            
        	byte[] key = {42, 79, -38, 81};
        	BigInteger tmp = new BigInteger(key);
        	System.out.println(tmp);
        	tmp = tmp.modPow(e, n);
        	tmp = tmp.modPow(d, n);
        	System.out.println(tmp);
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
    }  
}  