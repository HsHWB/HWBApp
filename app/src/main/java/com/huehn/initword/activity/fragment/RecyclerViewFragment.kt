package com.huehn.initword.activity.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.huehn.initword.R
import com.huehn.initword.basecomponent.base.BaseFragment
import com.huehn.initword.core.utils.Log.LogManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.internal.operators.flowable.FlowableFromIterable
import io.reactivex.internal.operators.observable.ObservableFromIterable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.android.synthetic.main.recycle_view_text_item.view.*
import kotlinx.android.synthetic.main.recycle_view_text_item.view.text


class RecyclerViewFragment : BaseFragment() {
    private lateinit var headTextRecycleAdapter : HeadTextRecycleAdapter
    private lateinit var gridViewAdapter: GridViewAdapter

    companion object{
        fun getInstance() : RecyclerViewFragment{
            var bundle : Bundle = Bundle()
            var recyclerViewFragment : RecyclerViewFragment = RecyclerViewFragment()
            recyclerViewFragment.arguments = bundle
            return recyclerViewFragment
        }
    }

    override fun getBundle(bundle: Bundle?) {
    }

    override fun getLayoutView(): Int {
        return R.layout.fragment_recycler
    }

    override fun initView(view: View?) {
        initGridView()
//        recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        headTextRecycleAdapter = HeadTextRecycleAdapter()
//        recycler_view.adapter = headTextRecycleAdapter
//        var runnable : Runnable = object : Runnable {
//            override fun run() {
//                activity?.let{
//                    if (it.isFinishing || it.isDestroyed){
//                        return
//                    }
//                    headTextRecycleAdapter.setDataList(getDataList())
//                    recycler_view.postDelayed(this, 500)
//                }
//            }
//
//        }
//        recycler_view.post(runnable)

        click_text.setOnClickListener(){
            Flowable.create<MutableList<ItemData>>({
                emitter: FlowableEmitter<MutableList<ItemData>> ->
                        emitter.onNext(getDataList())
            }, BackpressureStrategy.BUFFER)
//                    .flatMap (object : Function<MutableList<ItemData>, Flowable<ItemData>>{
//                        override fun apply(t: MutableList<ItemData>): Flowable<ItemData> {
//                            return FlowableFromIterable.fromIterable(t)
//                        }
//                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        takeIf { this@RecyclerViewFragment::gridViewAdapter.isInitialized }?.run {
                            gridViewAdapter.setDataList(it)
                        }
                    }
        }
    }

    fun initGridView() {
        activity?.run {
            gridViewAdapter = GridViewAdapter(this)
            grid_view.adapter = gridViewAdapter
            gridViewAdapter.setDataList(getDataList())
        }
    }

    fun getDataList(): MutableList<ItemData>{
        var list = mutableListOf<ItemData>()
        for (i in 0 .. 0){
            var itemData : ItemData
            if (i % 2 == 0){
                itemData = ItemData(
                        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3480070665,2380656812&fm=26&gp=0.jpg",
                        i.toString())
            }else{
                itemData = ItemData(null, i.toString())
            }
            list.add(itemData)
        }
        return list
    }

    class GridViewAdapter(var context: Context): BaseAdapter() {
        private lateinit var dataList : MutableList<ItemData>

        fun setDataList(list : MutableList<ItemData>){
            list?.takeIf { list.size > 0 }?.run {
                if (!this@GridViewAdapter::dataList.isInitialized) {
                    dataList = mutableListOf()
                } else {
                    dataList.clear()
                }
                dataList.addAll(list)
                notifyDataSetChanged()
            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var holder: Holder = Holder()
            var view = convertView
            convertView?.let {
                println("huehn holder is not null")
                view = convertView
                holder = convertView.tag as Holder
            } ?: let {
                println("huehn holder is null")
                view = LayoutInflater.from(context).inflate(R.layout.recycle_view_text_item, parent, false);
                holder.headImage = view?.findViewById(R.id.head_image)!!
                holder.text = view?.findViewById(R.id.text)!!
                view?.setTag(holder)
            }
            var itemData : ItemData = dataList[position]
            holder?.text?.setText(itemData.content)
            println("huehn holder url is : " + itemData.headUrl)
            if (!TextUtils.isEmpty(itemData.headUrl)){
                Glide.with(context)
                        .load(itemData.headUrl)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .skipMemoryCache(true)//开启之后图片就会闪烁。
                        // 设置是否跳过内存缓存，但不保证一定不被缓存（比如请求已经在加载资源且没设置跳过内存缓存，
                        // 这个资源就会被缓存在内存中）。
                        .dontAnimate()
                        .into(holder.headImage)

            }else{
//                Glide.with(context).load(context.resources.getDrawable(R.drawable.br_anchor_attestation))
//                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
//                        .skipMemoryCache(true)
//                        .dontAnimate()
//                        .into(holder.headImage)
                    holder.headImage.setImageResource(R.drawable.br_anchor_attestation)
            }

            return view!!
        }

        override fun getItem(position: Int): Any? {
            takeIf { this@GridViewAdapter::dataList.isInitialized }?.run {
                return dataList?.get(position)
            }?: kotlin.run {
                return null
            }
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            takeIf {
                this@GridViewAdapter::dataList.isInitialized
            }?.run {
                println("huehn getCount : run1 size : " + dataList.size)
                return dataList?.size
            }?: kotlin.run {
                println("huehn getCount : run2")
                return 0
            }
            return 0
        }

    }

    class Holder() {
        lateinit var headImage : ImageView
        lateinit var text : TextView
    }


    class HeadTextRecycleAdapter : RecyclerView.Adapter<HeadTextRecycleAdapter.Holder>() {

        private lateinit var context : Context
        private lateinit var dataList : MutableList<ItemData>

        fun setDataList(list : MutableList<ItemData>){
            list?.takeIf { list.size > 0 }?.run {
                if (!this@HeadTextRecycleAdapter::dataList.isInitialized) {
                    dataList = mutableListOf()
                } else {
                    dataList.clear()
                }
                dataList.addAll(list)
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
            context = p0.context
            return Holder(LayoutInflater.from(p0.context).inflate(R.layout.recycle_view_text_item, p0, false))
        }

        override fun getItemCount(): Int {
            return if (!this@HeadTextRecycleAdapter::dataList.isInitialized){
                0
            } else {
                dataList.size
            }
        }


        override fun onBindViewHolder(holder: Holder, position: Int) {
            var itemData : ItemData = dataList[position]
            itemData?.run {
                holder.itemView.text.setText(itemData.content)
                if (!TextUtils.isEmpty(itemData.headUrl)){
                    Glide.with(context)
                            .load(itemData.headUrl)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .skipMemoryCache(true)//开启之后图片就会闪烁。
                            // 设置是否跳过内存缓存，但不保证一定不被缓存（比如请求已经在加载资源且没设置跳过内存缓存，
                            // 这个资源就会被缓存在内存中）。
                            .dontAnimate()
                            .into(holder.itemView.head_image)

                }else{
                    Glide.with(context).load(context.resources.getDrawable(R.drawable.br_anchor_attestation))
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .skipMemoryCache(true)
                            .dontAnimate()
                            .into(holder.itemView.head_image)
//                    holder.itemView.head_image.setImageResource(R.drawable.br_anchor_attestation)
                }
                LogManager.d("huehn url : " + itemData.headUrl)
            }
        }

        class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private lateinit var headImage : ImageView
            private lateinit var text : TextView

            init {
                headImage = itemView.findViewById(R.id.head_image)
                text = itemView.findViewById(R.id.text)
            }

        }
    }
}

data class ItemData(var headUrl : String?, var content : String)