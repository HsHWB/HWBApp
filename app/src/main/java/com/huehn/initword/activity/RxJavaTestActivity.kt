package com.huehn.initword.activity

import android.content.res.AssetManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import com.google.gson.Gson
import com.huehn.initword.R
import com.huehn.initword.basecomponent.base.BaseActivity
import com.huehn.initword.bean.PersonData
import com.huehn.initword.bean.PersonDetail
import com.huehn.initword.core.app.App
import com.huehn.initword.core.utils.Rxjava.RxJavaUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.internal.operators.observable.ObservableCreate
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.io.File

class RxJavaTestActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)

        var textView = findViewById(R.id.textview) as TextView
        textView.setOnClickListener {
            baseRxJavaUse()
        }
    }


    fun getPersonJson() : PersonData{
        App.getApp().assets.open("person_json.json").bufferedReader().run {
            var data : String? = this.readLine()
            var buffer : StringBuffer = StringBuffer()
            while (data != null){
                buffer.append(data)
                data = this.readLine()
            }
            var jsonObject : JSONObject = JSONObject(buffer.toString())
            var gson : PersonData = Gson().fromJson(jsonObject.toString(), PersonData::class.java)
            return gson
        }

    }

    /**
     * 最基础的用法
     */
    fun baseRxJavaUse(){
        var observable : Disposable = Observable.create<PersonData>(ObservableOnSubscribe<PersonData>{
            emitter: ObservableEmitter<PersonData> ->
                emitter.onNext(getPersonJson())
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.person.forEach {
                        (name, age) -> println("huehn Person data : $name, $age")
                    }
                }, {
                    it.printStackTrace()
                })

//        var observable2 : Observable<String> = Observable.create{
////         create(((emitter: ObservableEmitter<String!>) → Unit)!)
//            //(emitter: ObservableEmitter<String!>) → Unit 是否等于 void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception;
//            //发射器，事件源，这个是SAM转换,ObservableOnSubscribe
//            //这个java接口里面只有一个抽象方法void subscribe(@NonNull ObservableEmitter<T> emitter)
//
//        }

        var observable3 : Observable<String> = ObservableCreate<String>(ObservableOnSubscribe {  })
    }

    fun mapRxJavaUse(){
        var observable : Disposable = Observable.create<PersonData>(ObservableOnSubscribe<PersonData>{
            emitter: ObservableEmitter<PersonData> ->
            emitter.onNext(getPersonJson())
        }).map {
            Function<PersonData, PersonDetail> {
                it.person[0]
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
//                    println("huehn Person data : ${it.}, ${it.}")
////                    it.person.forEach {
////                        (name, age) -> println("huehn Person data : $name, $age")
////                    }
                }, {
                    it.printStackTrace()
                })
    }

}
//var observable : Disposable = Observable.create<PersonData>(ObservableOnSubscribe<PersonData>{
//    emitter: ObservableEmitter<PersonData> ->
//    emitter.onNext(getPersonJson())
//}).map {
//    object : Function<PersonData, PersonDetail>{
//        override fun apply(t: PersonData): PersonDetail {
////                    return it.takeIf { it.person.isNotEmpty() }.run {
////                        it.person[0]
////                    }
//            return it.person[0]
//        }
//    }
//}.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe({
////                    println("huehn Person data : ${it.}, ${it.}")
//////                    it.person.forEach {
//////                        (name, age) -> println("huehn Person data : $name, $age")
//////                    }
//        }, {
//            it.printStackTrace()
//        })
//}

