package com.ofirkp.sockettest

data class Remote(var name: String, var drawableBackgroundID: Int, var drawableIconID: Int){
    companion object {
        val ITEMS = listOf<Remote>(
            Remote("Photoshop", R.drawable.maxresdefault, R.drawable.ic_photoshop_cc),
            Remote("Word", R.drawable.word_ui, R.drawable.word_icon),
            Remote("Chrome", R.drawable.chrome_ui, R.drawable.chrome_icon),
            Remote("Kodi", R.drawable.kodi_ui, R.drawable.kodi_icon),
            Remote("Photoshop", R.drawable.maxresdefault, R.drawable.ic_photoshop_cc),
            Remote("Word", R.drawable.word_ui, R.drawable.word_icon),
            Remote("Chrome", R.drawable.chrome_ui, R.drawable.chrome_icon),
            Remote("Kodi", R.drawable.kodi_ui, R.drawable.kodi_icon),
            Remote("Photoshop", R.drawable.maxresdefault, R.drawable.ic_photoshop_cc),
            Remote("Word", R.drawable.word_ui, R.drawable.word_icon),
            Remote("Chrome", R.drawable.chrome_ui, R.drawable.chrome_icon)
        )
    }
}