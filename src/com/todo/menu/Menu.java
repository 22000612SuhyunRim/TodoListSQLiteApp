package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("===================================");
        System.out.println("<�޴� ����>");
        System.out.println("1. ������ �߰� [add]");
        System.out.println("2. �ֱ����� ������ �ش� �޿� ���ؼ� �ѹ��� �߰� [add_week]");
        System.out.println("3. ������ ���� [del]");
        System.out.println("4. ������ ���� ���� [del_multi]");
        System.out.println("5. ������ ���� [edit]");
        System.out.println("6. ������ ��� [ls]");
        System.out.println("7. ī�װ� ��� [ls_cate]");
        System.out.println("8. �˻���� ������ �˻� [find]");
        System.out.println("9. �˻���� ī�װ� �˻� [find_cate]");
        System.out.println("10. ������ �̸������� ���� [ls_name]");
        System.out.println("11. ������ �̸��������� ���� [ls_name_desc]");
        System.out.println("12. ������ ��¥������ ���� [ls_date]");
        System.out.println("13. ������ �ֽż����� ���� [ls_date_desc]");
        System.out.println("14. ������ �Ϸ��ϱ� [comp]");
        System.out.println("15. ������ ���� �Ϸ��ϱ� [comp_multi]");
        System.out.println("16. �Ϸ�� ������ ���� [ls_comp]");
        System.out.println("17. ������ [exit]");
        System.out.println("===================================");
    }
    
    public static void prompt() {
    	System.out.print("\n����Ͻ� ��ɾ �Է��� �ּ���. > ");
    }
}