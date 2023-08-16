package learn.tcslibrary.data;

import learn.tcslibrary.App;
import learn.tcslibrary.data.mappers.AppUserMapper;
import learn.tcslibrary.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.ResultSet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {

    private JdbcTemplate jdbcTemplate;
    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public AppUser findByUsername(String username){
        List<String> roles=findRolesByUsername(username);
        final String sql = "select app_user_id, username, password_hash, enabled from app_user where username = ?;";
        List<AppUser> users=jdbcTemplate.query(sql, new AppUserMapper(roles), username);
        return users.isEmpty() ? null : users.get(0);
    }


    @Override
    @Transactional
    public List<String> findRolesByUsername(String username){
        final String sql = "select role.name " +
                "            from app_user user " +
                "            inner join app_user_role user_role on user.app_user_id=user_role.app_user_id " +
                "            inner join app_role role on role.app_role_id=user_role.app_role_id " +
                "            where username = ? ;";
        return jdbcTemplate.queryForList(sql, String.class, username);
    }

    @Override
    @Transactional
    public AppUser create(AppUser user){

        final String sql = "insert into app_user (username, password_hash) values (?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        int appUserId = keyHolder.getKey().intValue();
        user.setAppUserId(appUserId);
        int appRoleId = user.getAuthorities().contains(new SimpleGrantedAuthority("USER")) ? 1 : 2;
        insertUserRole(appUserId, appRoleId);

        return user;
    }

    public boolean insertUserRole(int appUserId, int appRoleId) {
        String sql = "insert into app_user_role (app_user_id, app_role_id) values (?, ?);";
        return jdbcTemplate.update(sql, appUserId, appRoleId)>=1;
    }

    @Override
    public List<AppUser>findAll(){
        final String sql = "select app_user_id, username, password_hash, enabled from app_user;";
        List<String> roles = findAllRoles();
        return jdbcTemplate.query(sql, new AppUserMapper(roles));
    }

    public List<String> findAllRoles(){
        final String sql = "select role.name " +
                "            from app_user user " +
                "            inner join app_user_role user_role on user.app_user_id=user_role.app_user_id " +
                "            inner join app_role role on role.app_role_id=user_role.app_role_id ;";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public int findAppIdByUsername(String username){
        AppUser user = findByUsername(username);
        return user.getAppUserId();
    }




}
