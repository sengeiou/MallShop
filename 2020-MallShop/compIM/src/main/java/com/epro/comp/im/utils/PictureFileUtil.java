package com.epro.comp.im.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.epro.comp.im.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.File;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * TODO
 *
 * @author stephen.shen
  * @date 2018-08-06 21:59
 */
public class PictureFileUtil {

    /**
     * 从相册选择图片
      * @param mContext     上下文信息
       */
    public static void openGalleryPic(Activity mContext,int requstcode) {
        // 进入相册 不需要的api可以不写
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_my_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(6)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .selectionMode(6 > 1 ?
                        PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
               // .minimumCompressSize(500)
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .openClickSound(cb_voice.isChecked())// 是否开启点击声音
               // .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(requstcode);//结果回调onActivityResult code
    }




    public static void openGalleryAudio(Activity mContext ,int requstcode ) {
        // 进入相册 不需要的api可以不写
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_my_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
             //   .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
              ///  .selectionMode(1 > 1 ?
               //         PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                // .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(requstcode);//结果回调onActivityResult code
    }


    public static void openFile(Activity mContext,int requestCode) {
        new MaterialFilePicker()
                .withActivity(mContext)
                .withRequestCode(requestCode)
                //       .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }

    public static final int RC_CAMERA = 11;
    @AfterPermissionGranted(RC_CAMERA)
    public static Uri takePhoto(Activity activity,int code){
        if (EasyPermissions.hasPermissions(activity, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Already have permission, do the thing
            Uri  imageUri = ext_takePhoto(activity,code);
            return imageUri;
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity,"需要打开相机等权限",
                    RC_CAMERA, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        return null;
    }

    private static Uri ext_takePhoto(Activity activity,int code) {
        Intent intent = takePhotoNow(activity);
        activity. startActivityForResult(intent, code);
        return intent.getParcelableExtra("imageUri");
    }

    private static Intent takePhotoNow(Activity activity) {
        // 跳转到系统的拍照界面
        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定照片存储位置为sd卡本目录下
        // 这里设置为固定名字 这样就只会只有一张temp图 如果要所有中间图片都保存可以通过时间或者加其他东西设置图片的名称
        // File.separator为系统自带的分隔符 是一个固定的常量
        String mTempPhotoPath = Environment.getExternalStorageDirectory().toString() + File.separator + "photo.jpeg";
        // 获取图片所在位置的Uri路径    *****这里为什么这么做参考问题2*****
          Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(activity,
                    activity.getApplication().getPackageName()+ ".fileProvider",
                    new File(mTempPhotoPath));
        } else {
            imageUri = Uri.fromFile(new File(mTempPhotoPath));
        }

        //下面这句指定调用相机拍照后的照片存储的路径
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intentToTakePhoto.putExtra("imageUri", imageUri);
        return intentToTakePhoto;
    }

    public  static  Uri startPhotoCrop(Activity activity,Uri uri,int width,int height,int requestCode) {
        Intent intent = startPhotoCropNow(uri,width,height);
        activity.startActivityForResult(intent, requestCode);
        return intent.getParcelableExtra("cropUri");
    }

    private static Intent startPhotoCropNow(Uri uri, int width, int height) {
        Uri outputUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg"));
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        //是否可裁剪
        intent.putExtra("corp", "true");
        intent.putExtra("scale", true);
        if (width!=0&&height!=0){
            //裁剪器高宽比
            intent.putExtra("aspectY", height);
            intent.putExtra("aspectX", width);
            //设置裁剪框高宽
            intent.putExtra("outputX", width);
            intent.putExtra("outputY", height);
        }
        //返回数据
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("cropUri", outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }

}
