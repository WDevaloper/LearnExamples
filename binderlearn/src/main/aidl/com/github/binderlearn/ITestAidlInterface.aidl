// ITestAidlInterface.aidl
package com.github.binderlearn;
import com.github.binderlearn.CallbacklInterface;

// Declare any non-default types here with import statements

interface ITestAidlInterface {
    void request(CallbacklInterface callback);
    void destroy(CallbacklInterface callback);
}
