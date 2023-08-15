package learn.tcslibrary.data.mappers;

import learn.tcslibrary.models.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static learn.tcslibrary.models.AppUser.convertRolesToAuthorities;

public class AppUserMapper implements RowMapper<AppUser> {
    private final List<String> roles;

    public AppUserMapper(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUser appUser=new AppUser();
        appUser.setAppUserId(rs.getInt("app_user_id"));
        appUser.setUsername(rs.getString("username"));
        appUser.setPassword(rs.getString("password_hash"));
        appUser.setEnabled(rs.getBoolean("enabled"));
        appUser.setAuthorities(convertRolesToAuthorities(roles));
        return appUser;
    }
}
