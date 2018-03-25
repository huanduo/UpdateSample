package com.updatesample;

import android.app.Application;
import android.os.Environment;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class MainApplication extends Application implements ReactApplication {
    private String path;
    private ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage()
            );
        }

//        @Override
//        protected ReactInstanceManager createReactInstanceManager() {
//            ReactMarker.logMarker(ReactMarkerConstants.BUILD_REACT_INSTANCE_MANAGER_START);
//            ReactInstanceManagerBuilder builder = ReactInstanceManager.builder()
//                    .setApplication(getApplication())
//                    .setJSMainModulePath("index")
//                    .addPackage(new MainReactPackage())
//                    .setUseDeveloperSupport(getUseDeveloperSupport())
//                    .setRedBoxHandler(getRedBoxHandler())
//                    .setJavaScriptExecutorFactory(getJavaScriptExecutorFactory())
//                    .setUIImplementationProvider(getUIImplementationProvider())
//                    .setInitialLifecycleState(LifecycleState.RESUMED);
//
//            String jsBundleFile = getJSBundleFile();
//            if (jsBundleFile != null) {
//                builder.setJSBundleFile(jsBundleFile);
//            } else {
//                builder.setBundleAssetName(Assertions.assertNotNull(getBundleAssetName()));
//            }
//
//            ReactInstanceManager reactInstanceManager = builder.build();
//            ReactMarker.logMarker(ReactMarkerConstants.BUILD_REACT_INSTANCE_MANAGER_END);
//            return reactInstanceManager;
//
//        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }

        /**
         * 如果不分模块 则可以在这里判断是否加载指定位置的bundle文件
         * @return
         */
        @Nullable
        @Override
        protected String getJSBundleFile() {
            File file = new File(path);
            if (file.exists()) {
                return path;
            } else {
                return super.getJSBundleFile();
            }
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
        path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "one" + File.separator + "index.android.bundle";
    }
}
