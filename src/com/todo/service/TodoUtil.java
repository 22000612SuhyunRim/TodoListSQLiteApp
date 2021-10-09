package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n리스트에 추가시킬 아이템의 제목을 입력하세요. > ");
		title = sc.next();
		
		if (l.isDuplicate(title)) {
			System.out.println("이미 추가된 아이템과 동일한 제목을 사용할 수 없습니다.");
			return;
		}
		
		System.out.print("아이템의 카테고리를 입력하세요. > ");
		category = sc.next();
		
		System.out.print("설명을 입력하세요. > ");
		sc.nextLine();
		desc = sc.nextLine();
		
		System.out.print("마감일자를 입력하세요. > ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, desc, null, category, due_date);
		if(l.addItem(t)>0)
			System.out.println("아이템이 추가되었습니다!");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n리스트에서 삭제할 아이템의 번호를 입력하세요. > ");
		int id = sc.nextInt();
		
		for (TodoItem item : l.getList()) {
			if (item.getId()==id) {
				System.out.println(item.toString());
				System.out.print("위 아이템을 삭제하시겠습니까? (y/n) > ");
				String select = sc.next();
				if(select.contains("y")) {
					if(l.deleteItem(id) > 0)
						System.out.println("아이템이 삭제되었습니다!");
					break;
				}
				else {
					System.out.println("아이템이 삭제되지 않았습니다.");
					break;
				}
			}
		}
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n리스트에서 수정하고 싶은 아이템의 번호를 입력하세요. > ");
		int id = sc.nextInt();
		/*if (l.getList().) {
			System.out.println("방금 입력하신 번호는 존재하지 않습니다.");
			return;
		}*/
		for (TodoItem item : l.getList()) {
			if (item.getId()==id) 
				System.out.println(item.toString());
		}
		
		System.out.print("새로운 제목을 입력하세요. > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("같은 제목으로 바꿀 수 없습니다.");
			return;
		}
		
		System.out.print("새로운 카테고리를 입력하세요. > ");
		String category = sc.next().trim();
		
		System.out.print("새로운 설명을 입력하세요. > ");
		sc.nextLine();
		String new_description = sc.nextLine().trim();
		
		System.out.print("새로운 마감일자를 입력하세요. > ");
		String due_date = sc.next().trim();
		
		
		TodoItem t = new TodoItem(new_title, new_description, category, due_date);
		t.setId(id);
		if(l.updateItem(t) > 0)
			System.out.println("아이템이 수정되었습니다!");
	}

	public static void listAll(TodoList l) {
		
		System.out.println("[전체 목록, 총 " + l.getCount() + "개]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("[전체 목록, 총 " + l.getCount() + "개]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.println("\n총 " + count + "개의 카테고리가 등록되어 있습니다.");
	}
	
	public static void find(TodoList l, String keyword) {
		int count = 0;
		
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void findCate(TodoList l, String keyword) {
		int count = 0;
		
		for(TodoItem item : l.getListCategory(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void completeItem(TodoList l, int number) {
		if(l.completeItem(number) > 0)
			System.out.println("해당 항목 완료 체크하였습니다.");
	}
	
	public static void listAll(TodoList l, int number) {
		int count = 0;
		
		for(TodoItem item : l.getList(number)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("\n총 " + count + "개의 항목이 완료되었습니다.");
	}
	
	public static void saveList(TodoList l, String filename) {
		int count = 0;
		
		try {
			Writer w = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
				count++;
			}
			w.close();
			System.out.println(count + "개의 아이템이 \"todolist.txt\" 파일에 저장되었습니다.");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		int count = 0;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String line;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String date = st.nextToken();
				count++;
				
				TodoItem t = new TodoItem(title, description, date, category, due_date);
				l.addItem(t);
			}
			br.close();
			if(count==0) System.out.println("\"todolist.txt\" 파일에 아이템이 존재하지 않습니다.");
			else System.out.println("\"todolist.txt\" 파일에서 " + count + "개의 아이템을 읽어왔습니다.\n");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("\"todolist.txt\" 파일이 없습니다.");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}