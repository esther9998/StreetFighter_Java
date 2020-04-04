import java.io.Serializable;

class Id implements Serializable { // 아이디가 같으면 같은
	String id;
	String passward;
	String intro;
	int win;
	int lose;

	Id() {

	}

	Id(String id, String passward) {
		this.id = id;
		this.passward = passward;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Id) {
			Id i = (Id) obj;
			return (this.id.equals(i.id)); // 아이디가 같을 때 같은 객체로 인식
		} else
			return false;
	}

	public int hashCode() {
		return 0; // 무조건 같게 인식
	}

}