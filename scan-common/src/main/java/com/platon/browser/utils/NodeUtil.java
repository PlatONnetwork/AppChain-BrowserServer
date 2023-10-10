package com.platon.browser.utils;

import com.alibaba.fastjson2.JSON;
import com.platon.bech32.Bech32;
import com.platon.crypto.ECDSASignature;
import com.platon.crypto.Hash;
import com.platon.crypto.Sign;
import com.platon.protocol.core.methods.response.PlatonBlock;
import com.platon.rlp.solidity.RlpEncoder;
import com.platon.rlp.solidity.RlpList;
import com.platon.rlp.solidity.RlpString;
import com.platon.rlp.solidity.RlpType;
import com.platon.utils.Numeric;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: dongqile
 * Date: 2019/1/10
 * Time: 10:52
 */
public class NodeUtil {
    private NodeUtil(){}

    /**
     * 通过区块计算节点公钥
     *
     * @param block
     * @return
     * @throws Exception
     */
    public static String getPublicKey(PlatonBlock.Block block){
        String publicKey = testBlock(block).toString(16);
        // 不足128前面补0
        int lack = 128 - publicKey.length();
        if(lack<=0) return publicKey;
        StringBuilder prefix = new StringBuilder();
        for (int i=0;i<lack;i++) prefix.append("0");
        prefix.append(publicKey);
        return prefix.toString();
    }

    public static BigInteger testBlock(PlatonBlock.Block block){
        String extraData = block.getExtraData();
        String signature = extraData.substring(66, extraData.length());
        System.out.println("signature:" + signature);
        byte[] msgHash = getMsgHash(block);

        System.out.println("msgHash bytes:" + JSON.toJSONString(msgHash));
        System.out.println("msgHash:" + Numeric.toHexString(msgHash));

        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        byte[] r = Arrays.copyOfRange(signatureBytes, 0, 32);
        byte[] s = Arrays.copyOfRange(signatureBytes, 32, 64);
        return Sign.recoverFromSignature( v, new ECDSASignature(new BigInteger(1, r), new BigInteger(1, s)), msgHash);
    }

    public static void main1(String[] args){
        String sealHash = "0x1658b1674330f880010485239b3d938956d2eabaa21cfee66a70391b0d265336";
        byte[] msgHash = HexUtil.decode(sealHash);
        System.out.println("msgHash bytes:" + JSON.toJSONString(msgHash));
        String signature = "0x1fa2a586b8dcc0fa0326e9eaacee10a52d87530b44df6b329093b2ff6da333be102c41cb8dc13fb28072b0082ba87f2cc5af3a4ac8591eb2a770f3dcfbcc4f5b01";
        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        byte[] r = Arrays.copyOfRange(signatureBytes, 0, 32);
        byte[] s = Arrays.copyOfRange(signatureBytes, 32, 64);
        BigInteger id =  Sign.recoverFromSignature( v, new ECDSASignature(new BigInteger(1, r), new BigInteger(1, s)), msgHash);
        String publicKey = id.toString(16);
        System.out.println("PublicKey:"+publicKey);
    }

    public static void main(String[] args){
        PlatonBlock.Block block = new PlatonBlock.Block();
        block.setParentHash("0xef99021b30d7caab822ff0629ba213a9be72d241b89cfe4a555d231066445f32");
        block.setMiner("hsk1rft6tyjvz9535yfq49f0ajrmqpd6z8n45uxnck");
        block.setStateRoot("0xd6577cfd7dc0eb4ac937edfa1ff878fe253044799c28448664cbd101e1a9db69");
        block.setTransactionsRoot("0xd84e10d732662132f79c0f156d2a5744ac086af1f2dd7e79061e59cfd00ad0db");
        block.setReceiptsRoot("0x27c539ea5678c560835cc2beadbc28fa773b46bd1e89a3f62626cd13d57bdffb");
        block.setLogsBloom("0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000080000000000000008000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000000000020000000000080000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        block.setNumber("0x1");
        block.setGasLimit("0xc012af6");
        block.setGasUsed("0x5404");
        block.setTimestamp("0x18B18C5D086");
        block.setExtraData("0xda830104008868736b636861696e86676f312e3230856c696e7578000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001e49d73");
        block.setNonce("0x03eaa12a2503b4968818bdc0f95f7da28c21417a83fa4220364b8524869f280e6f7bef29c3a4c2314fa7f5b0bdfb28c40c9e05c823ea853a14c0a3898dcce4024a1f6a59599f7adff1ad7f05b2b4d3f9af");
        System.out.println(cn.hutool.core.util.HexUtil.encodeHex(getMsgHash(block)));
        System.out.println(block.getGasLimit().toString());
    }


    public static void main2(String[] args){

        List<String> hashList = Arrays.asList("0x434d1f70ac19b62cec572ed4093f4c7986cf309c9c17bcdfe8eb20f93bc3f652",
                "0x833df8dd35bb47721a659ad30e87393395e2610c88dee8857c43b8051a219301",
                "0x0097135e93166ce91914decb1e4a01a93e3a6b1c364e42037ef6f0ab42217b3a",
                "0x6e780b7411deaf08e1b4719848cda98d7e39ba325adccf4330f0134019b1124b"
                );

        List<String> signatureList = Arrays.asList("8b29b1ecb5a4ebf5379b36ca2ade82fe50577cee74f5266cc9d3d28b6bb51bd6acf338c73c7112c050d56666cf809a6661434bdee583277a8561af2edfcc72f0000000000000000000000000000000000000000000000000000000000023e09e1",
                "0x29da703abe65ae485629725c29c9798184376d8df631f58fc18dd7aef96f57695afc895f44a5093681c348fb1799b93289e6ed4b539ffa80a271ea6dbcdb225f00",
                "0x92518e73099f6217bca1786e4f2e22245756e6ff34feae64480826bd291596e90c6924ec687bbd33638244192b7ac8a468083f73d475e1cb76bdcb5bb3ef628500",
                "0x3e7fc4688f3c1cd6668e03f95ead8cd4dac38191e90897a9f9842c7b6b7e693f45247a0a1b147da938d72f417e48fd71bd13c7de0558dbd6e9932be6349e082701"
                );

        for (int i=0; i<=3; i++){
            new Thread(new MyJob(hashList.get(i), signatureList.get(i))).start();
        }
    }


    static class MyJob implements Runnable{
        private String sealHash;
        private String signature;

        public MyJob(String sealHash, String signature) {
            this.sealHash = sealHash;
            this.signature = signature;
        }

        @Override
        public void run() {
            byte[] msgHash = HexUtil.decode(sealHash);
            byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
            byte v = signatureBytes[64];
            byte[] r = Arrays.copyOfRange(signatureBytes, 0, 32);
            byte[] s = Arrays.copyOfRange(signatureBytes, 32, 64);
            BigInteger id =  Sign.recoverFromSignature( v, new ECDSASignature(new BigInteger(1, r), new BigInteger(1, s)), msgHash);
            String publicKey = id.toString(16);
            System.out.println("PublicKey:"+publicKey);
        }
    }


    private static byte[] getMsgHash(PlatonBlock.Block block) {
        byte[] signData = encode(block);
        return Hash.sha3(signData);
    }

    public static byte[] encode(PlatonBlock.Block block) {
        List<RlpType> values = asRlpValues(block);
        RlpList rlpList = new RlpList(values);
        return RlpEncoder.encode(rlpList);
    }

    static List<RlpType> asRlpValues(PlatonBlock.Block block) {
        List<RlpType> result = new ArrayList<>();
        //ParentHash  common.Hash    `json:"parentHash"       gencodec:"required"`
        result.add(RlpString.create(decodeHash(block.getParentHash())));
        //Coinbase    common.Address `json:"miner"            gencodec:"required"`
        result.add(RlpString.create(decodeAddress(block.getMiner())));
        //Root        common.Hash    `json:"stateRoot"        gencodec:"required"`
        result.add(RlpString.create(decodeHash(block.getStateRoot())));
        //TxHash      common.Hash    `json:"transactionsRoot" gencodec:"required"`
        result.add(RlpString.create(decodeHash(block.getTransactionsRoot())));
        //ReceiptHash common.Hash    `json:"receiptsRoot"     gencodec:"required"`
        result.add(RlpString.create(decodeHash(block.getReceiptsRoot())));
        //Bloom       Bloom          `json:"logsBloom"        gencodec:"required"`
        result.add(RlpString.create(decodeHash(block.getLogsBloom())));
        //Number      *big.Int       `json:"number"           gencodec:"required"`
        result.add(RlpString.create(block.getNumber()));
        //GasLimit    uint64         `json:"gasLimit"         gencodec:"required"`
        result.add(RlpString.create(block.getGasLimit()));
        //GasUsed     uint64         `json:"gasUsed"          gencodec:"required"`
        result.add(RlpString.create(block.getGasUsed()));
        //Time        *big.Int       `json:"timestamp"        gencodec:"required"`
        result.add(RlpString.create(block.getTimestamp()));
        //Extra       []byte         `json:"extraData"        gencodec:"required"`
        result.add(RlpString.create(decodeHash(block.getExtraData().substring(0, 66))));
        //Nonce       BlockNonce     `json:"nonce"`
        result.add(RlpString.create(decodeHash(block.getNonceRaw())));
        return result;
    }

    static byte[] decodeHash(String hex) {
        return Hex.decode(Numeric.cleanHexPrefix(hex));
    }

    static byte[] decodeAddress(String address) {
        return Bech32.addressDecode(address);
    }

}
