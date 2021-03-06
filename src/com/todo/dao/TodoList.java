package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByDateDesc;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	Connection conn;
	
	public TodoList() {
		this.conn = DbConnect.getConnection();
	}

	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = ("insert into list (title, memo, category, current_date, due_date, is_completed, expected_time, difficulty)" 
						+ "values (?, ?, ?, ?, ?, ?, ?, ?);");
			
			int records = 0;
			while((line=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				int is_completed = Integer.parseInt(st.nextToken());
				String expected_time = st.nextToken();
				String difficulty = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				pstmt.setInt(6, is_completed);
				pstmt.setString(7, expected_time);
				pstmt.setString(8, difficulty);
				
				int count = pstmt.executeUpdate();
				if(count>0) records++;
				pstmt.close();
			}
			System.out.println(records + "개의 아이템을 불러왔습니다.");
			br.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int addItem(TodoItem t) {
		String sql = ("insert into list (title, memo, category, current_date, due_date, expected_time, difficulty)" 
					+ "values (?, ?, ?, ?, ?, ?, ?);");
		PreparedStatement pstmt;
		
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getExpected_time());
			pstmt.setString(7, t.getDifficulty());
			
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public int checkDays(int month) {
		int days=0;
		
		switch(month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days=31;
				break;
			case 2:
				days=28;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				days=30;
				break;
		}
		return days;
	}
	
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?, expected_time=?, difficulty=?" 
					+ "where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getExpected_time());
			pstmt.setString(7, t.getDifficulty());
			pstmt.setInt(8, t.getId());
			count=pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public int completeItem(int number) {
		int count=0;
		
		String sql = "update list set is_completed=1 " + "where id = ?;";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			count=pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getList(int number) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql = "SELECT * FROM list where is_completed=1;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String expected_time = rs.getString("expected_time");
				String difficulty = rs.getString("difficulty");
				TodoItem t = new TodoItem(title, description, category, due_date, expected_time, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt=conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String expected_time = rs.getString("expected_time");
				String difficulty = rs.getString("difficulty");
				TodoItem t = new TodoItem(title, description, category, due_date, expected_time, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?;";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String expected_time = rs.getString("expected_time");
				String difficulty = rs.getString("difficulty");
				TodoItem t = new TodoItem(title, description, category, due_date, expected_time, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
		stmt = conn.createStatement();
		String sql = "SELECT DISTINCT category FROM list;";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			String category = rs.getString("category");
			list.add(category);
		}
		stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		String sql = "SELECT * FROM list WHERE category = ?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String expected_time = rs.getString("expected_time");
				String difficulty = rs.getString("difficulty");
				TodoItem t = new TodoItem(title, description, category, due_date, expected_time, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering==0) {
				sql+= " desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String expected_time = rs.getString("expected_time");
				String difficulty = rs.getString("difficulty");
				TodoItem t = new TodoItem(title, description, category, due_date, expected_time, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("\\n[정렬된 전체 목록, 총\" + list.size() + \"개]\"");
		for (TodoItem myitem : list) {
			System.out.println((list.indexOf(myitem)+1) + ". " + myitem.toString());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}
	
	public void sortByDateDesc() {
		Collections.sort(list, new TodoSortByDateDesc());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : getList()) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
}
