package com.chx.wxpay.wxpayutils.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.chx.wxpay.wxpayutils.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
  private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

  private Button gotoBtn, regBtn, launchBtn, checkBtn, scanBtn;

  // IWXAPI 是第三方app和微信通信的openapi接口
  private IWXAPI api;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setContentView(R.layout.entry);

    // 通过WXAPIFactory工厂，获取IWXAPI的实例
    api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);

    //注意：
    //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
    try {
      api.handleIntent(getIntent(), this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    setIntent(intent);
    api.handleIntent(intent, this);
  }


  @Override
  public void onReq(BaseReq baseReq) {

  }

//  b. 实现IWXAPIEventHandler接口，微信发送的请求将回调到onReq方法，发送到微信请求的响应结果将回调到onResp方法
  @Override
  public void onResp(BaseResp baseResp) {
//    if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//      WXPay.getInstance(this,getWXAppId()).onResp(baseResp.errCode);
//      finish();
//    }

    if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
      if(baseResp.errCode==0){
        Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
      }
      else {
        Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
      }
      finish();
    }

  }
}
