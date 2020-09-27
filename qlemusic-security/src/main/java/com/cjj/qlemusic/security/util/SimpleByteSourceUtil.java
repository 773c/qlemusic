package com.cjj.qlemusic.security.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * 自定义SimpleByteSource类将其序列化
 */
public class SimpleByteSourceUtil implements ByteSource, Serializable{

    private static final long serialVersionUID = 5528101080905698238L;

    private  byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public SimpleByteSourceUtil() {
    }
    public SimpleByteSourceUtil(byte[] bytes) {
        this.bytes = bytes;
    }
    public SimpleByteSourceUtil(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }
    public SimpleByteSourceUtil(String string) {
        this.bytes = CodecSupport.toBytes(string);
    }
    public SimpleByteSourceUtil(ByteSource source) {
        this.bytes = source.getBytes();
    }
    public SimpleByteSourceUtil(File file) {
        this.bytes = (new SimpleByteSourceUtil.BytesHelper()).getBytes(file);
    }

    public SimpleByteSourceUtil(InputStream inputStream){
        this.bytes = (new SimpleByteSourceUtil.BytesHelper()).getBytes(inputStream);
    }


    @Override
    public byte[] getBytes() {
        return this.bytes;
    }

    @Override
    public String toHex() {
        if(this.cachedHex == null) {
            this.cachedHex = Hex.encodeToString(this.getBytes());
        }
        return this.cachedHex;
    }

    @Override
    public String toBase64() {
        if(this.cachedBase64 == null) {
            this.cachedBase64 = Base64.encodeToString(this.getBytes());
        }

        return this.cachedBase64;
    }

    @Override
    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    public String toString() {
        return this.toBase64();
    }

    public int hashCode() {
        return this.bytes != null && this.bytes.length != 0? Arrays.hashCode(this.bytes):0;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(o instanceof ByteSource) {
            ByteSource bs = (ByteSource)o;
            return Arrays.equals(this.getBytes(), bs.getBytes());
        } else {
            return false;
        }
    }

    private static final class BytesHelper extends CodecSupport {
        private BytesHelper() {
        }

        public byte[] getBytes(File file) {
            return this.toBytes(file);
        }

        public byte[] getBytes(InputStream stream) {
            return this.toBytes(stream);
        }
    }
}
