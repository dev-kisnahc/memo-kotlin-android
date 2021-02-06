package com.kisnahc.memo_kotlin_android

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("DEPRECATION")
@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity(), OnDeleteListener {


    private lateinit var recyclerView : RecyclerView
    lateinit var db : MemoDatabase
    var memoList : List<MemoEntity> = listOf<MemoEntity>()
    private lateinit var btn_add : Button
    private lateinit var et_memo : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add = findViewById(R.id.btn_add)
        et_memo = findViewById(R.id.et_memo)
        recyclerView = findViewById(R.id.recyclerView)

        db = MemoDatabase.getInstance(this)!!
        btn_add.setOnClickListener{
            val memo = MemoEntity(null, et_memo.text.toString())
            et_memo.setText("")
                insertMemo(memo)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        getAllMemos()
    }





    //1. Insert Data
    fun insertMemo(memo : MemoEntity){
        //1. MainThread vs WorkerThread(Background Thread)

        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().insert(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }

        insertTask.execute()


    }

    //2. Get Data
    fun getAllMemos(){
        val getTask = (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(memoList)
            }
        }).execute()

    }

    //3. Delete Data
    fun deleteMemo(memo: MemoEntity){
        val deleteTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }
        deleteTask.execute()

    }

    //4. Set RecyclerView
    fun setRecyclerView(memoList: List<MemoEntity>){
        recyclerView.adapter = MyAdapter(this,memoList, this)
    }

    companion object {
        private  const val TAG = "MainActivity"
    }

    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
    }


}