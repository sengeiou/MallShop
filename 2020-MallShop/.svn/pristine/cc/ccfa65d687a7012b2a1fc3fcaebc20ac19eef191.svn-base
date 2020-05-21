package com.mike.baselib.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.mike.baselib.utils.LogTools


abstract class BaseLazyLoadFragment<V : IBaseView, P : IPresenter<V>> : BaseFragment<V, P>() {
    /**
     * 是否可见状态 为了避免和[Fragment.isVisible]冲突 换个名字
     */
    private var isFragmentVisible: Boolean = false
    /**
     * 标志位，View已经初始化完成。
     * 2016/04/29
     * 用isAdded()属性代替
     * 2016/05/03
     * isPrepared还是准一些,isAdded有可能出现onCreateView没走完但是isAdded了
     */
    private var isPrepared: Boolean = false
    /**
     * 是否第一次加载  页面只会加载一次,除非重建fragment
     */
    private var isFirstLoad = true
    /**
     * <pre>
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     * 一般用于PagerAdapter需要刷新各个子Fragment的场景
     * 不要new 新的 PagerAdapter 而采取reset数据的方式
     * 所以要求Fragment重新走initData方法
     * 故使用 [BaseFragment.setForceLoad]来让Fragment下次执行initData
    </pre> *
     */
    private var forceLoad = false
    /**
     * 是否在viewpager中
     */
    private var isViewPageFragment = false

    private val log = LogTools("BaseLazyLoadFragment_" + this::class.java.simpleName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null && bundle.size() > 0) {
            initVariables(bundle)
        }
        log.d("onCreate()")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 若 viewpager 不设置 setOffscreenPageLimit 或设置数量不够
        // 销毁的Fragment onCreateView 每次都会执行(但实体类没有从内存销毁)
        // 导致initData反复执行,所以这里注释掉
        //isFirstLoad = true

        // 2016/04/29
        // 取消 isFirstLoad = true的注释 , 因为上述的initData本身就是应该执行的
        // onCreateView执行 证明被移出过FragmentManager initData确实要执行.
        // 如果这里有数据累加的Bug 请在initViews方法里初始化您的数据 比如 list.clear();
        isPrepared = true
        log.d("onViewCreated()")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        log.d("onCreateView()")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        log.d("onStart()")
    }

    override fun onResume() {
        super.onResume()
        log.d("onResume()")
    }

    override fun onPause() {
        super.onPause()
        log.d("onPause()")
    }

    override fun onStop() {
        super.onStop()
        log.d("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        log.d("onDestroy()")
        isFragmentVisible = false
        isPrepared = false
        isFirstLoad = true
        forceLoad = false
        isViewPageFragment = false
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        log.d("setUserVisibleHint()" + isVisibleToUser)
        if (isVisibleToUser) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        log.d("onActivityCreated()")
        if (isViewPageFragment) {
            lazyLoadPre()
        } else {
            onVisible()
        }
        super.onActivityCreated(savedInstanceState)
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     * visible.
     */
    override fun onHiddenChanged(hidden: Boolean) {
        log.d("onHiddenChanged()" + hidden)
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    protected fun onVisible() {
        isFragmentVisible = true
        lazyLoadPre()
    }

    protected fun onInvisible() {
        isFragmentVisible = false
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected fun lazyLoadPre() {
        if (isPrepared && isFragmentVisible) {
            if (forceLoad || isFirstLoad) {
                //forceLoad = false
                isFirstLoad = false
                lazyLoad()
            }
            //lazyLoad()
        }
    }

    abstract fun lazyLoad()

    override fun onDestroyView() {
        super.onDestroyView()
        log.d("onDestroyView()")
        isPrepared = false
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        log.d("onAttach()")
    }

    override fun onDetach() {
        super.onDetach()
        log.d("onDetach()")
    }


    /**
     * 被ViewPager移出的Fragment 下次显示时会从getArguments()中重新获取数据
     * 所以若需要刷新被移除Fragment内的数据需要重新put数据 eg:
     * Bundle args = getArguments();
     * if (args != null) {
     * args.putParcelable(KEY, info);
     * }
     */
    fun initVariables(bundle: Bundle) {

    }


    fun isPrepared(): Boolean {
        return isPrepared
    }

    /**
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     */
    fun setForceLoad(forceLoad: Boolean) {
        this.forceLoad = forceLoad
    }

    fun isFirstLoad(): Boolean {
        return isFirstLoad
    }

    fun setViewPageFragment(isViewPageFragment: Boolean): BaseLazyLoadFragment<V, P> {
        this.isViewPageFragment = isViewPageFragment
        return this
    }

    fun isFragmentVisible(): Boolean {
        return isFragmentVisible
    }
}