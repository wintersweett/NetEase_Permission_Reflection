package com.netease.permission.reflection;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.print.PrinterId;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.netease.ioc.library.annotations.ContentView;
import com.netease.ioc.library.annotations.OnClick;
import com.netease.permission.library.PermissionManager;
import com.netease.permission.library.annotation.IPermission;
import com.netease.permission.reflection.base.PermissionActivity;

@ContentView(R.layout.activity_main)
public class MainActivity extends PermissionActivity {

    private static final int CZZ = 111; // 拍照权限请求标识码
    private static final int MULTIP_PERMISSION_CODE = 222; // 位置、联系人权限请求标识码

    private static final int SINGLE_PERMISSION_CODE = 111;

    private static final int REPEAT_VIEW_PERMISSION_CODE = 333;

//在想
    private View  view;


    @OnClick(R.id.singlePermission)
    public void siginPermission(){
        singlePermissionReqest(SINGLE_PERMISSION_CODE);
    }

    @OnClick(R.id.multiPermission)
    public void multipPermission(){
        multipPermissionReqest(MULTIP_PERMISSION_CODE);
    }

    @OnClick(R.id.repeatPermission)
    public void repeatPermission(){
        repeatPermissionReqest(REPEAT_VIEW_PERMISSION_CODE);
    }

    void singlePermissionReqest(int req) {
        if (PermissionManager.hasPermissions(this, permission.CAMERA)) {

        } else {
            PermissionManager.requestPermissions(this,req, permission.CAMERA);
        }
    }

    void multipPermissionReqest(int req) {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS};
        if (PermissionManager.hasPermissions(this, perms)) { // 授权通过
            Toast.makeText(this, "授权通过！", Toast.LENGTH_SHORT).show(); //就是成功之后调用自己的业务逻辑 ，这个需要知道是哪个view
        } else { // 请求授权
            PermissionManager.requestPermissions(this, req, perms);
        }
    }

    void repeatPermissionReqest(int req) {
        if (PermissionManager.hasPermissions(this, permission.CAMERA)) {

        } else {
            PermissionManager.requestPermissions(this,req, permission.CAMERA);
        }
    }




    @IPermission(SINGLE_PERMISSION_CODE)
    private void singlePermissionRet(int req) {
        batchPermission(req);
    }


    @IPermission(MULTIP_PERMISSION_CODE)
    private void multipPermissionRet(int req) {
        batchPermission(req);
    }
    @IPermission(REPEAT_VIEW_PERMISSION_CODE)
    private void repeatPermissionRet(int req) {
        batchPermission(req);
    }


    private void batchPermission(int req)
    {
        switch (req) {
            case SINGLE_PERMISSION_CODE:     //multi permission 现在是点击是1    invoke后 是222
                String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS};
                if(PermissionManager.hasPermissions(this,perms)){ //有权限
                    Toast.makeText(this, "授权通过！", Toast.LENGTH_SHORT).show();  //主要这个方法需要view
                    Log.d("ws","req:"+req);

                }else {
                   // PermissionManager.requestPermissions(this, LOCATION_CONTACTS_CODE, perms);
                    Toast.makeText(this, "mul view  camera", Toast.LENGTH_SHORT).show();
                }
                break;
            case MULTIP_PERMISSION_CODE: //single permission

                break;
            case REPEAT_VIEW_PERMISSION_CODE: //single permission
                Toast.makeText(this, "repeat view  camera", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
