import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class ItemTest {

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(Include.NON_DEFAULT)
	public static class Device implements Serializable {
		private final String type;

		@JsonCreator
		public Device(@JsonProperty("type") String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(Include.NON_NULL)
	public static class Item implements Serializable {

		private final String identifier;
		private Object data;

		@JsonCreator
		public Item(@JsonProperty("identifier") String identifier,
				@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "identifier", defaultImpl = JsonNode.class, visible = true)//
		@JsonSubTypes({ @Type(value = Device.class, name = "deviceType") })//
		@JsonProperty("data") Object data) {
			this.identifier = identifier;
			this.data = data;
		}

		public Object getData() {
			return data;
		}

		public String getIdentifier() {
			return identifier;
		}

		@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "identifier", defaultImpl = JsonNode.class, visible = true)
		@JsonSubTypes({ @Type(value = Device.class, name = "deviceType") })
		@JsonProperty("data")
		public void setData(Object data) {
			this.data = data;
		}
	}

	@Test
	public void test() throws Exception {
		final String json = "[{\"identifier\": \"deviceType\",\"data\": { \"type\": \"some-type\"}}, {\"identifier\": \"weather\",\"data\": { \"city\": \"LAKELAND\", \"state\": \"FL\", \"temperature\": \"63.0\", \"iconkey\": \"04\", \"description\": \"Clouds and sun\"} ]";
		System.out.println("Input Json: " + json);
		final List<Item> object = new ObjectMapper().reader(new TypeReference<List<Item>>() {
		}).readValue(json);
		assertTrue(object.get(0).getData() instanceof Device);
	}

}
