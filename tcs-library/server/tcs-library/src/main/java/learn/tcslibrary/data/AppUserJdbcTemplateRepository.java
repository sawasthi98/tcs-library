package learn.tcslibrary.data;

import learn.tcslibrary.data.mappers.AppUserMapper;
import learn.tcslibrary.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class AppUserJdbcTemplateRepository {

    private JdbcTemplate jdbcTemplate;


    public AppUser findByUsername(String username){
        List<String> roles=findRolesByUsername(username);
        final String sql = "select app_user_id, username, password_hash, enabled from app_user where username = ?;";
        List<AppUser> users=jdbcTemplate.query(sql, new AppUserMapper(roles), username);
        return users.get(0);
    }

    public List<String> findRolesByUsername(String username){
        final String sql = "select user.username, user.app_user_id, user_role.app_role_id, role.name, role.app_role_id " +
                "from app_user user inner join app_user_role user_role on user_role.app_user_id=user_role.app_user_id " +
                "inner join app_role role on role.app_role_id=user_role.app_role_id " +
                "and username = ? ;";
        return jdbcTemplate.query(sql, username);
    }



}
