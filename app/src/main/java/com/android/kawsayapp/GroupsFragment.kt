package com.android.kawsayapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

// --- Define tus modelos de datos (pueden ir en archivos separados) ---
data class ForumPost(val author: String, val title: String, val body: String, val votes: Int)

// --- Adaptadores para los RecyclerView (pueden ir en archivos separados) ---
class GroupCategoryAdapter(private val categories: List<String>, private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<GroupCategoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.group_category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.name.text = category
        holder.itemView.setOnClickListener { onItemClick(category) }
    }

    override fun getItemCount() = categories.size
}

class ForumPostAdapter(private val posts: List<ForumPost>) : RecyclerView.Adapter<ForumPostAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById(R.id.post_author)
        val title: TextView = view.findViewById(R.id.post_title)
        val body: TextView = view.findViewById(R.id.post_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forum_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.author.text = post.author
        holder.title.text = post.title
        holder.body.text = post.body
    }

    override fun getItemCount() = posts.size
}


// --- El Fragmento Principal ---
class GroupsFragment : Fragment() {

    private lateinit var groupListContainer: ConstraintLayout
    private lateinit var postListContainer: ConstraintLayout
    private lateinit var buttonBack: ImageButton
    private lateinit var textSelectedGroupTitle: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Inicializar vistas principales
        groupListContainer = view.findViewById(R.id.group_list_container)
        postListContainer = view.findViewById(R.id.post_list_container)
        buttonBack = view.findViewById(R.id.button_back_to_groups)
        textSelectedGroupTitle = view.findViewById(R.id.text_selected_group_title)

        // --- Configurar la lista de categorías de grupos ---
        val rvGroups = view.findViewById<RecyclerView>(R.id.recycler_view_groups)
        rvGroups.layoutManager = LinearLayoutManager(context)
        val groupCategories = listOf("Manejo de la Ansiedad", "Viviendo con TDAH", "Superando la Depresión", "Crecimiento Personal")
        rvGroups.adapter = GroupCategoryAdapter(groupCategories) { selectedCategory ->
            showPostList(selectedCategory)
        }

        // --- Configurar la lista de publicaciones (con datos de ejemplo) ---
        val rvPosts = view.findViewById<RecyclerView>(R.id.recycler_view_posts)
        rvPosts.layoutManager = LinearLayoutManager(context)
        val samplePosts = listOf(
            ForumPost("Carlos Ruiz", "Recomendaciones de apps de meditación", "Busco una app con meditaciones guiadas para la ansiedad...", 8),
            ForumPost("Ana Pérez", "¿Técnicas de respiración para ataques de pánico?", "He probado la respiración 4-7-8 pero me gustaría conocer otras...", 12),
            ForumPost("Luis Gómez", "Compartiendo un pequeño logro de hoy", "Hoy logré salir a caminar 15 minutos aunque no tenía ganas. ¡Me siento orgulloso!", 25)
        )
        rvPosts.adapter = ForumPostAdapter(samplePosts)

        // Configurar el botón para volver
        buttonBack.setOnClickListener {
            showGroupList()
        }
    }

    private fun showPostList(groupName: String) {
        textSelectedGroupTitle.text = groupName
        groupListContainer.visibility = View.GONE
        postListContainer.visibility = View.VISIBLE
    }

    private fun showGroupList() {
        postListContainer.visibility = View.GONE
        groupListContainer.visibility = View.VISIBLE
    }
}