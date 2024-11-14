package cped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public void addMember(Member member) {
        String sql = "INSERT INTO Members (name, email, category, joining_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseSetup.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getCategory());
            pstmt.setDate(4, Date.valueOf(member.getJoiningDate()));
            pstmt.executeUpdate();

            System.out.println("Member added: " + member.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Members";

        try (Connection conn = DatabaseSetup.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Member member = new Member(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("category"),
                        rs.getString("joining_date").toString());
                member.setId(rs.getInt("id"));
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
}
