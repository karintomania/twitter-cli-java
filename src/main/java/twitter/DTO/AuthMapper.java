package twitter.DTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface AuthMapper {

	@Select("SELECT * from auth WHERE user_id = #{id}")
	Auth getAuth(String id);

	@Insert("INSERT into auth (user_id, access_token, access_token_secret) VALUES (#{userId}, #{accessToken}, #{accessTokenSecret})")
	@Options(useGeneratedKeys = false, keyProperty = "user_id")
	void insert(Auth auth);
}