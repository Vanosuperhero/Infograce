package com.example.infograce.network.models

import com.google.gson.annotations.SerializedName

data class TilesetsItemDTO(

	@field:SerializedName("created_by_client")
	val createdByClient: String? = null,

	@field:SerializedName("visibility")
	val visibility: String? = null,

	@field:SerializedName("created")
	val created: String? = null,

	@field:SerializedName("center")
	val center: List<Double?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("modified")
	val modified: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("filesize")
	val filesize: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("tileset_precisions")
	val tilesetPrecisions: TilesetPrecisionsDTO? = null,

	@field:SerializedName("status")
	val status: String? = null
)