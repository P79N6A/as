#include <jni.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <string>

//用于加密解密的密码
char password[] = "I AM MI MA";



//static {
////加载动态库.so文件，注意不用写lib前缀，系统会默认添加
//System.loadLibrary("crypt-lib");
//}

class string;

JNIEXPORT void JNICALL
Java_com_yaoguang_company_yaoguangndk_Cryptor_cryptFile(JNIEnv *env, jclass type, jstring src_,
                                                        jstring dest_) {

    const char *c_src = env->GetStringUTFChars(src_, NULL);
    const char *c_dest = env->GetStringUTFChars(dest_, NULL);

    FILE *f_read = fopen(c_src, "rb");
    FILE *f_write = fopen(c_dest, "wb");

    //判断文件是否正确打开
    if (f_read == NULL || f_write == NULL) {
        printf("file open field");
        return;
    }

    //一次读取一个字符
    int ch;
    int i = 0;
    int pwd_len = strlen(password);
    while ((ch = fgetc(f_read)) != EOF) {
        //通过异或运算进行加密
        fputc(ch ^ password[i % pwd_len], f_write);
        i++;
    }

    //关闭文件
    fclose(f_read);
    fclose(f_write);

    env->ReleaseStringUTFChars(src_, c_src);
    env->ReleaseStringUTFChars(dest_, c_dest);
}

JNIEXPORT void JNICALL
Java_com_yaoguang_company_yaoguangndk_Cryptor_decryptFile(JNIEnv *env, jclass type, jstring src_,
                                                          jstring dest_) {

    const char *c_src = env->GetStringUTFChars(src_, NULL);
    const char *c_dest = env->GetStringUTFChars(dest_, NULL);

    FILE *f_read = fopen(c_src, "rb");
    FILE *f_write = fopen(c_dest, "wb");

    //判断文件是否正确打开
    if (f_read == NULL || f_write == NULL) {
        printf("file open field");
        return;
    }

    //一次读取一个字符
    int ch;
    int i = 0;
    int pwd_len = strlen(password);
    while ((ch = fgetc(f_read)) != EOF) {
        //通过异或运算进行加密
        fputc(ch ^ password[i % pwd_len], f_write);
        i++;
    }

    //关闭文件
    fclose(f_read);
    fclose(f_write);

    env->ReleaseStringUTFChars(src_, c_src);
    env->ReleaseStringUTFChars(dest_, c_dest);
}

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_yaoguang_company_yaoguangndk_Cryptor_getClientPrivateKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string clientPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKbNojYr8KlqKD/y"
            "COd7QXu3e4TsrHd4sz3XgDYWEZZgYqIjVDcpcnlztwomgjMj9xSxdpyCc85GOGa0"
            "lva1fNZpG6KXYS1xuFa9G7FRbaACoCL31TRv8t4TNkfQhQ7e2S7ZktqyUePWYLlz"
            "u8hx5jXdriErRIx1jWK1q1NeEd3NAgMBAAECgYAws7Ob+4JeBLfRy9pbs/ovpCf1"
            "bKEClQRIlyZBJHpoHKZPzt7k6D4bRfT4irvTMLoQmawXEGO9o3UOT8YQLHdRLitW"
            "1CYKLy8k8ycyNpB/1L2vP+kHDzmM6Pr0IvkFgnbIFQmXeS5NBV+xOdlAYzuPFkCy"
            "fUSOKdmt3F/Pbf9EhQJBANrF5Uaxmk7qGXfRV7tCT+f27eAWtYi2h/gJenLrmtke"
            "Hg7SkgDiYHErJDns85va4cnhaAzAI1eSIHVaXh3JGXcCQQDDL9ns78LNDr/QuHN9"
            "pmeDdlQfikeDKzW8dMcUIqGVX4WQJMptviZuf3cMvgm9+hDTVLvSePdTlA9YSCF4"
            "VNPbAkEAvbe54XlpCKBIX7iiLRkPdGiV1qu614j7FqUZlAkvKrPMeywuQygNXHZ+"
            "HuGWTIUfItQfSFdjDrEBBuPMFGZtdwJAV5N3xyyIjfMJM4AfKYhpN333HrOvhHX1"
            "xVnsHOew8lGKnvMy9Gx11+xPISN/QYMa24dQQo5OAm0TOXwbsF73MwJAHzqaKZPs"
            "EN08JunWDOKs3ZS+92maJIm1YGdYf5ipB8/Bm3wElnJsCiAeRqYKmPpAMlCZ5x+Z"
            "AsuC1sjcp2r7xw==";
    return env->NewStringUTF(clientPrivateKey.c_str());
}

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_yaoguang_company_yaoguangndk_Cryptor_getClientPublicKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string clientPrivateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmzaI2K/Cpaig/8gjne0F7t3uE"
            "7Kx3eLM914A2FhGWYGKiI1Q3KXJ5c7cKJoIzI/cUsXacgnPORjhmtJb2tXzWaRui"
            "l2EtcbhWvRuxUW2gAqAi99U0b/LeEzZH0IUO3tku2ZLaslHj1mC5c7vIceY13a4h"
            "K0SMdY1itatTXhHdzQIDAQAB";
    return env->NewStringUTF(clientPrivateKey.c_str());
}