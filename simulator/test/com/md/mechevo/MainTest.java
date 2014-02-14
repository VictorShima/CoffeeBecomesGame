package com.md.mechevo;

import org.junit.Test;

public class MainTest {
    @Test
	public void testMain() {
        final String JSON = "{" +
                "  \"map\": {" +
                "    \"width\": 800," +
                "    \"height\": 800" +
                "  }," +
                "  \"obstacles\": [" +
                "    {" +
                "      \"x\": 50," +
                "      \"y\": 50," +
                "      \"radius\": 10" +
                "    }" +
                "  ]," +
                "  \"players\": [" +
                "    {" +
                "      \"teamId\": 0," +
                "      \"weapons\": [" +
                "        \"\"," +
                "        \"\"," +
                "        \"\"" +
                "      ]," +
                "      \"x\": 100," +
                "      \"y\": 200," +
                "      \"angle\": 50," +
                "      \"algorithm\": [" +
                "        {" +
                "          \"conditions\": [" +
                "            {" +
                "              \"name\": \"TrueCondition\"," +
                "              \"param\": \"\"" +
                "            }" +
                "          ]," +
                "          \"actions\": [" +
                "            {" +
                "              \"name\": \"MoveInLine\"," +
                "              \"param\": \"\"" +
                "            }" +
                "          ]" +
                "        }" +
                "      ]" +
                "    }" +
                "  ]" +
                "}";

        Main.main(new String[] {
                JSON
        });
	}
}
