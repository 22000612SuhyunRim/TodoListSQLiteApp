package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("===================================");
        System.out.println("<메뉴 사용법>");
        System.out.println("1. 아이템 추가 [add]");
        System.out.println("2. 주기적인 아이템 해당 달에 한해서 한번에 추가 [add_week]");
        System.out.println("3. 아이템 삭제 [del]");
        System.out.println("4. 아이템 동시 삭제 [del_multi]");
        System.out.println("5. 아이템 수정 [edit]");
        System.out.println("6. 아이템 목록 [ls]");
        System.out.println("7. 카테고리 목록 [ls_cate]");
        System.out.println("8. 검색어로 아이템 검색 [find]");
        System.out.println("9. 검색어로 카테고리 검색 [find_cate]");
        System.out.println("10. 아이템 이름순으로 정렬 [ls_name]");
        System.out.println("11. 아이템 이름역순으로 정렬 [ls_name_desc]");
        System.out.println("12. 아이템 날짜순으로 정렬 [ls_date]");
        System.out.println("13. 아이템 최신순으로 정렬 [ls_date_desc]");
        System.out.println("14. 아이템 완료하기 [comp]");
        System.out.println("15. 아이템 동시 완료하기 [comp_multi]");
        System.out.println("16. 완료된 아이템 정렬 [ls_comp]");
        System.out.println("17. 나가기 [exit]");
        System.out.println("===================================");
    }
    
    public static void prompt() {
    	System.out.print("\n사용하실 명령어를 입력해 주세요. > ");
    }
}