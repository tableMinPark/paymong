package com.paymong.paypoint.global.pay;

public class Pay {
    public static String getMap(String brand){
        brand = brand.toLowerCase();
        String ret = null;
        if( (ret = isCafe(brand))!=null)
            return ret;
        if( (ret = isDelvery(brand))!=null)
            return ret;
        if( (ret = isCombeni(brand))!=null)
            return ret;
        if( (ret = isSingPc(brand))!=null)
            return ret;
        if( (ret = isBreadIce(brand))!=null)
            return ret;
        if( (ret = isBurger(brand))!=null)
            return ret;
        if( (ret = isShop(brand))!=null)
            return ret;
        if( (ret = isMovie(brand))!=null)
            return ret;
        return null;
    }

    public static String isCafe(String brand){
        if( brand.contains("스타벅스") )
            return "스타벅스";
        if( brand.contains("컴포즈") )
            return "컴포즈커피";
        if( brand.contains("이디야") )
            return "이디야";
        if( brand.contains("할리스") )
            return "할리스";
        if( brand.contains("투썸") )
            return "투썸플레이스";
        if( brand.contains("메가커피") || (brand.contains("메가") && brand.contains("커피")))
            return "메가커피";
        if( brand.contains("파스쿠찌") )
            return "파스쿠찌";
        if( brand.contains("블루보틀") )
            return "블루보틀";
        if( brand.contains("하이오") )
            return "하이오커피";
        if( brand.contains("블루샥") )
            return "블루샥";
        if( brand.contains("커피") || brand.contains("카페") || brand.contains("coffee") || brand.contains("cafe"))
            return "카페";

        return null;
    }

    public static String isCombeni(String brand) {
        if( brand.contains("씨유") || brand.contains("cu"))
            return "CU";
        if( brand.contains("이마트") || brand.contains("emart"))
            return "이마트24";
        if( brand.contains("GS")  || brand.contains("지에스"))
            return "GS25";
        if( brand.contains("세븐일레븐"))
            return "세븐일레븐";
        return null;
    }
    
    public static String isSingPc(String brand){
        if( brand.contains("동전") || brand.contains("코인") || brand.contains("노래")  || brand.contains("싱어"))
            return "노래방";
        if( brand.contains("피시") || brand.contains("피씨")  || brand.contains("pc")  || brand.contains("제닉스아레나"))
            return "PC방";
        return null;
    }

    public static String isBurger(String brand){
        if( brand.contains("맥도날드") )
            return "맥도날드";
        if( brand.contains("버거킹") )
            return "버거킹";
        if( brand.contains("롯데리아") )
            return "롯데리아";
        if( brand.contains("맘스") )
            return "맘스터치";
        if( brand.contains("kfc") )
            return "KFC";
        return null;
    }

    public static String isMovie(String brand){
        if( brand.contains("cgv") )
            return "CGV";
        if( brand.contains("메가박스") || (brand.contains("mega") && brand.contains("box")) )
            return "메가박스";
        if( (brand.contains("롯데") && brand.contains("시네마")) )
            return "롯데시네마";
        return null;
    }

    public static String isShop(String brand){
        if( brand.contains("올리브영") )
            return "올리브영";
        if( brand.contains("나이키") )
            return "나이키";
        if( brand.contains("아디다스") )
            return "아디다스";
        if( brand.contains("다이소") || brand.contains("주식회사 신세대"))
            return "다이소";
        return null;
    }

    public static String isDelvery(String brand){
        if( brand.contains("요기요") )
            return "요기요";
        if( brand.contains("배달의민족") || brand.contains("우아한형제들"))
            return "배달의민족";
        if( brand.contains("쿠팡") || brand.contains("coupang"))
            return "쿠팡";
        return null;
    }

    public static String isBreadIce(String brand){
        if( brand.contains("던킨") )
            return "던킨도너츠";
        if( brand.contains("파리바게뜨") )
            return "파리바게트";
        if( brand.contains("뚜레쥬르") )
            return "뚜레쥬르";
        if( brand.contains("설빙") )
            return "설빙";
        if( brand.contains("베스킨") || brand.contains("배스킨"))
            return "배스킨라빈스31";
        return null;
    }
}
