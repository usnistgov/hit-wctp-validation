package gov.nist.hit.wctp;

import java.util.List;

import org.dom4j.Node;

public class NodeInfo {
	private String value = "";
	private Node value_node;
	private List<Node> values;
	private String path = "";
	
	
	public NodeInfo(String value, String path) {
		this.value = value;
		this.path = path;
	}
	
	public NodeInfo(Node value_node, String path) {
		this.value_node = value_node;
		this.path = path;
	}

	public NodeInfo(List<Node> values, String path) {
		this.values = values;
		this.path = path;
	}


	public String getValue() {
		return value;
	}
	
	public Node getValue_Node() {
		return value_node;
	}

	public List<Node> getValues() {
		return values;
	}


	public String getPath() {
		return path;
	}
	
	
	
	
	
	
	
	
}
