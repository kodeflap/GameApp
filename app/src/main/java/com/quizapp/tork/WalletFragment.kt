package com.quizapp.tork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.quizapp.tork.model.User
import com.quizapp.tork.model.Withdraw
import kotlinx.android.synthetic.main.fragment_wallet.*
import kotlinx.android.synthetic.main.fragment_wallet.view.*


class WalletFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var user = User()
        val view = inflater.inflate(R.layout.fragment_wallet, container, false)
        val database = FirebaseFirestore.getInstance()
        database.collection("users")
            .document(FirebaseAuth.getInstance().uid!!)
            .get().addOnSuccessListener { documentSnapshot ->
                user = documentSnapshot.toObject(User::class.java)!!
                currentCoin.text = user.coins.toString()
            }
        view.send.setOnClickListener(View.OnClickListener {view ->

            if(user.coins!! > 5000)
            {
                val uid = FirebaseAuth.getInstance().uid
                val paypal = paypal.text
                val req = Withdraw(uid, paypal.toString(),user.name)

                database.collection("withdraw")
                    .document(FirebaseAuth.getInstance().uid!!)
                    .set(req).addOnSuccessListener {
                        Toast.makeText(context,"Request send successfully",Toast.LENGTH_LONG).show()
                    }
            }
            else
            {
                Toast.makeText(context,"You need more than 5000 to withdraw ",Toast.LENGTH_LONG).show()
            }
        })
        return view
    }
}