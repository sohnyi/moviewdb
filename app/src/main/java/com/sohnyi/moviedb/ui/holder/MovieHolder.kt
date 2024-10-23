package com.sohnyi.moviedb.ui.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sohnyi.moviedb.databinding.ItemMoviewBinding
import com.sohnyi.moviedb.extionsion.load
import com.sohnyi.moviedb.model.Movie


class MovieHolder(
    private val binding: ItemMoviewBinding,
) : ViewHolder(binding.root) {


    fun bind(movie: Movie) {

        // 排名
        binding.tvPosition.text =(absoluteAdapterPosition + 1).toString()

        // 封面
        binding.ivPoster.load(movie.posterPath, 16)

        // 标题
        binding.tvTitle.text = movie.title ?: ""

        // 原标题
        if (movie.originalTitle != null && movie.title != movie.originalTitle) {
            binding.tvOriginalTitle.text = movie.originalTitle
        } else {
            binding.tvOriginalTitle.text = ""
        }


        // 上映日期
        binding.releaseDate.text = movie.releaseDate
        // 评分
        binding.tvRate.text = movie.rate.toString()
    }

    companion object {
        fun create(parent: ViewGroup): MovieHolder {
            val binding = ItemMoviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MovieHolder(binding)
        }
    }
}