package twitter.DTO;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class AuthRepository{
	SqlSessionFactory sqlSessionFactory;

    //private constructor to avoid client applications to use constructor
    public AuthRepository(){
		String resource = "mybatis-config.xml";

		try(InputStream inputStream = Resources.getResourceAsStream(resource);){
			sqlSessionFactory =
			new SqlSessionFactoryBuilder().build(inputStream);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
	}

	public Auth getAuth(String id){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AuthMapper mapper = session.getMapper(AuthMapper.class);
			Auth auth = mapper.getAuth(id);
			return auth;
		}
	}

	public void insert(Auth auth){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AuthMapper mapper = session.getMapper(AuthMapper.class);
			mapper.insert(auth);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}