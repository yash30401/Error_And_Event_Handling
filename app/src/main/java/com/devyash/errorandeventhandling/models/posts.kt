package com.devyash.errorandeventhandling.models

import com.google.gson.annotations.SerializedName

data class posts(
    @SerializedName("postItems")
    val postItems:List<postsItem>
)