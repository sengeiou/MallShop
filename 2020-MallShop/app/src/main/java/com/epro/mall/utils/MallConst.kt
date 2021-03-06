package com.epro.mall.utils

class MallConst {
    companion object {
        const val VERSION="/platform" //初始版本
        const val V1="/platform/v1" //v1版本
        const val LOGIN = "$VERSION/auth"
        const val REGISTER = "$VERSION/open/register"
        const val GET_VFCODE = "$VERSION/open/getVcode"
        const val GET_USERVFCODE = "$VERSION/getVCodeByUserAccount"
        const val FIND_PASSWORD = "$VERSION/open/pwdreset" //忘记密码
        const val LOGOUT = "$VERSION/logout" //退出登录
        const val MODIFY_PASSWORD = "$VERSION/passwordModify"
        const val BIND_ACCOUNT = "$VERSION/resetPassword"
        const val GET_HOME_SHOP_LIST = "$VERSION/open/selectAppShop"
        const val GET_NEARLY_SHOP_LIST = "$VERSION/open/selectLatelyShop"
        const val GET_HOME_BANNER_LIST = "$VERSION/open/selectAppAdvert"
        const val SEARCH = "/open/search"
        const val GET_SHOP_INFO = "$VERSION/open/selectShopDesc"
        const val GET_SHOP_GOODSLIST_HOT = "/open/shopGoodsList/hot/"
        const val GET_SHOP_GOODSLIST_RECOMMEND = "/open/shopGoodsList/suggest/"
        const val GET_SHOP_HOME_VIEW = "$VERSION/open/shopHomeView/{shopId}"
        const val GET_SHOP_GOODSLIST_NEW = "$VERSION/open/selectShopNewGoods/{shopId}"
        const val GET_SHOP_GOODSLIST_CATEGORY = "$VERSION/open/restProduct/findGoodsList"
        const val GET_SHOP_GOODSLIST_SEARCH = "/open/shopsearch/all/"
        const val FOLLOW_SHOP = "/subscribe/" //关注店铺
        const val GET_GOODS_DETAIL = "$VERSION/open/restProduct/findProductByGoodId"
        const val GET_CARTGOODS_LIST = "$VERSION/cart/detail"
        const val ADD_CARTGOODS = "$VERSION/cart/add"
        const val MODIFY_CARTGOODS = "$VERSION/cart/incr"
        const val DELETE_CARTGOODS = "$VERSION/cart/delete"
        const val GET_CARTGOODS_COUNT = "$VERSION/cart/count"
        const val CREATE_ORDER = "$VERSION/order/submit"
        const val GET_ORDER_LIST = "$VERSION/order/list"      //订单列表
        const val GET_ORDER_DETAIL = "$VERSION/order/detail/" //订单详情
        const val CANCEL_ORDER = "$VERSION/order/cancel/"     //取消订单
        const val DELETE_ORDER = "$VERSION/order/delete/"    //删除订单
        const val GET_PAYTYPE_LIST = "/paytype/list"
        const val GET_SEARCH_SUGGESTS = "/paytype/list"
        const val ASSOCIATION_ACCOUNT = "$VERSION/puserProvider/bindAccount" //关联账号绑定
        const val UNBIND_ACCOUNT = "$VERSION/puserProvider/unbindAccount"    //解绑关联账号
        const val ASSOCIATION_ACCOUNT_LIST = " $VERSION/puserProvider/list"  //关联账号列表

        const val ABOUT_APP = "$VERSION/open/version/selectNewVersion"  //查询最新版本

        const val GET_MY_INFO = "$VERSION/puser/detail"  //我的基本信息
        const val UPDATE_IMAGE = "$VERSION/open/image/upload"      //上传图片
        const val MODIFY_IMAGE = "$VERSION/puser/updateAvatar"  //修改图片
        const val GET_MY_ADDRESS_LIST = "$VERSION/userAddress/list" //收货地址列表
        const val ADD_ADDRESS = "$VERSION/userAddress/add" //新增地址
        const val UPDATE_ADDRESS = "$VERSION/userAddress/update"
        const val DELETE_ADDRESS = "$VERSION/userAddress/delete/{id}"
        const val KEY_SEARCH_RECORDS="search_records"
        const val CHANGE_ACCOUNT_EAMIL = "$VERSION/puser/changeAccount" //绑定手机或者邮箱
        const val CHECK_ACCOUNT_BIND = "$VERSION/puser/validUnbindCode" //检查绑定的账号
        const val SCAN_CODE_ORDER_LIST = "$VERSION/scporder/list"  //扫码购订单列表
        const val SCAN_CODE_ORDER_DETAIL = "$VERSION/scporder/detail"  //扫码购订单详情
        const val GET_GOODS_BY_BARCODE = "$VERSION/open/restProduct/findGoodsByBarCode"  //通过条码找到商品
        const val CREATE_SCANBUY_ORDER = "$VERSION/scporder/genOrder"  //扫码购创建订单
        const val CHECK_ORDER_PAY="$VERSION/order/paycheck" // 检查支付状态
        const val ORDER_PAY="$VERSION/order/pay" // 去支付
        const val ORDER_AGAIN="$VERSION/order/again" // 再来一单
        const val ACCOUNT_BIND = "$VERSION/puser/bindAccount" //绑定账号
        const val SEARCH_SHOPLIST = "$VERSION/open/search/shopsearch" //搜索店铺
        const val SEARCH_GOODSLIST = "$VERSION/open/search/goodssearch" //商品搜索
        const val SEARCH_ASSOCIATE = "$VERSION/open/search/prefixsearch" //搜索联想
        const val GET_CITY_LIST = "$VERSION/cityList/{level}" //获取城市列表
        const val GET_SYSTEM_TIME="$VERSION/open/getTime"


        const val GET_LOCAL_CITY_LIST="get_local_city_list"

       // EP:箱密码登录, MP:手机密码登录, MV:手机验证码登录 ，FB:facebook登录，TW:推特登录
        const val LOGIN_TYPE_EP="ep"
        const val LOGIN_TYPE_MP="mp"
        const val LOGIN_TYPE_MV="mv"
        const val LOGIN_TYPE_FB="fb"
        const val LOGIN_TYPE_TW="tw"

        //验证码类型(1-登录,2-注册,3-找回密码,4-修改密码)
        const val VF_TYPE_LOGIN=1
        const val VF_TYPE_REGISTER=2
        const val VF_TYPE_FINDPASSWORD=3
        const val VF_TYPE_MODIFY_PASSWORD=4

        //支付类型
         const val PAY_MODE_ZFB=2 //支付宝
         const val PAY_MODE_PAYPAL=3 //paypal支付
         const val PAY_MODE_WX=4 //微信


        //金额单位
        const val AMOUNT_UNIT_HK="HK "

       // 送货方式(0.自提 1.配送)
        const val DISTRIBUTION_TYPE_SELFTAKE = 0 //自提
        const val DISTRIBUTION_TYPE_SEND = 1 //配送


        //响应码(200-支付成功,10051-付款失败, 10028未知)
        const val PAY_SUCCESS=200 //支付成功
        const val PAY_FAILED=10051 //支付失败
        const val PAY_UNKOWN=10028 //支付未知
        const val PAY_CANCEL=-100 //支付取消

        const val ALIPAY_ACTION="alipay"

        //选择相册类型
        const val Camera_TYPE = 0 //相机
        const val Gallery_TYPE = 1  //相册
        const val Cancel_TYPE = 2

        //跳转位置1:店铺首页2:商品首页
        const val JUMP_TO_SHOP = "1"
        const val JUMP_TO_GOODS = "2"

       // 1：店铺默认配送距离  2：自定义  3：不支持配送
        const val DELIVERY_MODE_SHOPDEFAULT="1"
        const val DELIVERY_MODE_CUSTOM="2"
        const val DELIVERY_MODE_NO="3"
    }
}