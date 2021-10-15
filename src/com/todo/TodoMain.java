package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		l.importData("todolist.txt");
		boolean isList = false;
		boolean quit = false;
		String keyword;
		
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "del_multi":
				TodoUtil.deleteMultiItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "find":
				keyword = sc.nextLine().trim();
				TodoUtil.find(l, keyword);
				break;
				
			case "find_cate":
				keyword = sc.nextLine().trim();
				TodoUtil.findCate(l, keyword);
				break;

			case "ls_name":
				System.out.println("제목순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
			
			case "comp":
				int number = sc.nextInt();
				TodoUtil.completeItem(l, number);
				break;
				
			case "comp_multi":
				TodoUtil.completeMultiItem(l);
				break;
				
			case "ls_comp":
				System.out.println("완료된 아이템(들)을 정렬하였습니다.");
				TodoUtil.listAll(l, 1);
				break;	
			
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("메뉴 사용법에 적힌 명령어를 참고해서 다시 입력해 주세요. [help]");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		TodoUtil.saveList(l, "todolist.txt");
	}
}
