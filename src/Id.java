import java.io.Serializable;

class Id implements Serializable { // ���̵� ������ ����
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
			return (this.id.equals(i.id)); // ���̵� ���� �� ���� ��ü�� �ν�
		} else
			return false;
	}

	public int hashCode() {
		return 0; // ������ ���� �ν�
	}

}