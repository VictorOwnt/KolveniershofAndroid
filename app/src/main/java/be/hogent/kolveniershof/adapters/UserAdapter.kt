package be.hogent.kolveniershof.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import be.hogent.kolveniershof.R
import be.hogent.kolveniershof.model.User
import kotlinx.android.synthetic.main.row_user.view.*


class UserAdapter(context: Context,userList: List<User>) : ArrayAdapter<User>(context,0,userList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view : View? = convertView
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.row_user,parent, false);
        }
        var viewHolder: UserViewHolder?
        viewHolder = view!!.tag as? UserViewHolder
        if (viewHolder == null) {
            viewHolder = UserViewHolder()
            viewHolder.name = view.name
            //viewHolder.firstName = view!!.firstName
            //viewHolder.lastName = view!!.LastName
            view.tag = viewHolder
        }
        val user: User? = getItem(position)
        viewHolder.id = user!!.id
        viewHolder.name!!.text = user!!.firstName+ " " + user!!.lastName
        return view
    }

}
class UserViewHolder {
    var id : String? = null
    var name : TextView? = null
}

