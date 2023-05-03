package com.paymong.common.code

import com.paymong.common.R

enum class CharacterCode(
    val resourceCode: Int,
    val codeName: String,
    val code: String
) {
    CH000(R.drawable.ch000, "화산알", "CH000"),
    CH001(R.drawable.ch001, "석탄알", "CH001"),
    CH002(R.drawable.ch002, "황금알", "CH002"),
    CH003(R.drawable.ch003, "목성알", "CH003"),
    CH004(R.drawable.ch004, "지구알", "CH004"),
    CH005(R.drawable.ch005, "바람알", "CH005"),
    CH100(R.drawable.ch100, "별몽", "CH100"),
    CH101(R.drawable.ch101, "동글몽", "CH101"),
    CH102(R.drawable.ch102, "네몽", "CH102"),
    CH200(R.drawable.ch200, "안씻은 별별몽", "CH200"),
    CH210(R.drawable.ch200, "무난한 별별몽", "CH210"),
    CH220(R.drawable.ch200, "유쾌한 별별몽", "CH220"),
    CH230(R.drawable.ch200, "완벽한 별별몽", "CH230"),
    CH201(R.drawable.ch201, "안씻은 둥글몽", "CH201"),
    CH211(R.drawable.ch201, "무난한 둥글몽", "CH211"),
    CH221(R.drawable.ch201, "유쾌한 둥글몽", "CH221"),
    CH231(R.drawable.ch201, "완벽한 둥글몽", "CH231"),
    CH202(R.drawable.ch202, "안씻은 나네몽", "CH202"),
    CH212(R.drawable.ch202, "무난한 나네몽", "CH212"),
    CH222(R.drawable.ch202, "유쾌한 나네몽", "CH222"),
    CH232(R.drawable.ch202, "완벽한 나네몽", "CH232"),
    CH203(R.drawable.ch203, "까몽", "CH203"),
    CH300(R.drawable.ch300, "병든 별뿔몽", "CH300"),
    CH310(R.drawable.ch300, "평범한 별뿔몽", "CH310"),
    CH320(R.drawable.ch300, "매력적인 별뿔몽", "CH320"),
    CH330(R.drawable.ch300, "엘리트 별뿔몽", "CH330"),
    CH301(R.drawable.ch301, "병든 땡글몽", "CH301"),
    CH311(R.drawable.ch301, "평범한 땡글몽", "CH311"),
    CH321(R.drawable.ch301, "매력적인 땡글몽", "CH321"),
    CH331(R.drawable.ch301, "엘리트 땡글몽", "CH331"),
    CH302(R.drawable.ch302, "병든 마미무메몽", "CH302"),
    CH312(R.drawable.ch302, "평범한 마미무메몽", "CH312"),
    CH322(R.drawable.ch302, "매력적인 마미무메몽", "CH322"),
    CH332(R.drawable.ch302, "엘리트 마미무메몽", "CH332"),
    CH303(R.drawable.ch303, "쌔까몽", "CH303"),
    CH444(R.drawable.none,"", "CH444")
}