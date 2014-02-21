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
                "              \"param\": [\"\"]" +
                "            }" +
                "          ]," +
                "          \"actions\": [" +
                "            {" +
                "              \"name\": \"MoveInLine\"," +
                "              \"param\": [\"MOVE\", \"FORWARD\", \"10\"]" +
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

	/**
	 * Test for miniguns and an AI that only attacks
	 */
	@Test
	public void testMain2() {
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
				"        \"Minigun\"," +
				"        \"\"," +
				"        \"\"" +
				"      ]," +
				"      \"x\": 100," +
				"      \"y\": 500," +
				"      \"angle\": 0," +
				"      \"algorithm\": [" +
				"        {" +
				"          \"conditions\": [" +
				"            {" +
				"              \"name\": \"EnemySpotted\"," +
				"              \"param\": [\"\"]" +
				"            }" +
				"          ]," +
				"          \"actions\": [" +
				"            {" +
				"              \"name\": \"Attack\"," +
				"              \"param\": [\"LEFT\"]" +
				"            }" +
				"          ]" +
				"        }" +
				"      ]" +
				"    } ," +

				"    {" +
				"      \"teamId\": 1," +
				"      \"weapons\": [" +
				"        \"Minigun\"," +
				"        \"\"," +
				"        \"\"" +
				"      ]," +
				"      \"x\": 500," +
				"      \"y\": 500," +
				"      \"angle\": 180," +
				"      \"algorithm\": [" +
				"        {" +
				"          \"conditions\": [" +
				"            {" +
				"              \"name\": \"TrueCondition\"," +
				"              \"param\": [\"\"]" +
				"            }" +
				"          ]," +
				"          \"actions\": [" +
				"            {" +
				"              \"name\": \"MoveInLine\"," +
				"              \"param\": [\"MOVE\", \"FORWARD\", \"1\"]" +
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

	/**
	 * Test for homing missiles
	 */
	@Test
	public void testMain3() {
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
				"        \"HomingMissileLauncher\"," +
				"        \"\"," +
				"        \"\"" +
				"      ]," +
				"      \"x\": 100," +
				"      \"y\": 500," +
				"      \"angle\": 0," +
				"      \"algorithm\": [" +
				"        {" +
				"          \"conditions\": [" +
				"            {" +
				"              \"name\": \"EnemySpotted\"," +
				"              \"param\": [\"\"]" +
				"            }" +
				"          ]," +
				"          \"actions\": [" +
				"            {" +
				"              \"name\": \"Attack\"," +
				"              \"param\": [\"LEFT\"]" +
				"            }" +
				"          ]" +
				"        }" +
				"      ]" +
				"    } ," +

				"    {" +
				"      \"teamId\": 1," +
				"      \"weapons\": [" +
				"        \"HomingMissileLauncher\"," +
				"        \"\"," +
				"        \"\"" +
				"      ]," +
				"      \"x\": 550," +
 "      \"y\": 490,"
						+				"      \"angle\": 180," +
				"      \"algorithm\": [" +
				"        {" +
				"          \"conditions\": [" +
				"            {" +
				"              \"name\": \"EnemySpotted\"," +
				"              \"param\": [\"\"]" +
				"            }" +
				"          ]," +
				"          \"actions\": [" +
				"            {" +
				"              \"name\": \"MoveInLine\"," +
				"              \"param\": [\"MOVE\", \"FORWARD\", \"10\"]" +
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

	/**
	 * Complex test.
	 */
	@Test
	public void testMain4() {
		final String JSON =
				"{" + "  \"map\": {" + "    \"width\": 800," + "    \"height\": 800" + "  },"
						+ "  \"obstacles\": [" + "    {" + "      \"x\": 100," + "      \"y\": 50,"
						+ "      \"radius\": 10" + "    }," + "	 {" + "      \"x\": 200,"
						+ "      \"y\": 50," + "      \"radius\": 10" + "    }" + "  ],"
						+ "  \"players\": [" + "    {" + "      \"teamId\": 0,"
						+ "      \"weapons\": [" + "        \"RocketLauncher\","
						+ "        \"RocketLauncher\"," + "        \"MineLauncher\"" + "      ],"
						+ "      \"x\": 50," + "      \"y\": 50," + "      \"angle\": 0,"
						+ "      \"algorithm\": [" + "        {" + "          \"conditions\": ["
						+ "            {" + "              \"name\": \"EnemySpotted\","
						+ "              \"param\": [\"\"]" + "            }" + "          ],"
						+ "          \"actions\": [" + "            {"
						+ "              \"name\": \"FaceOpponent\","
						+ "              \"param\": []" + "            }," + "            {"
						+ "              \"name\": \"Attack\","
						+ "              \"param\": [\"LEFT\"]" + "            },"
						+ "            {" + "              \"name\": \"Attack\","
						+ "              \"param\": [\"RIGHT\"]" + "            }" + "          ]"
						+ "        }," + "        {" + "          \"conditions\": ["
						+ "            {" + "              \"name\": \"WeaponReady\","
						+ "              \"param\": [\"CENTER\"]" + "            }"
						+ "          ]," + "          \"actions\": [" + "            {"
						+ "              \"name\": \"Attack\","
						+ "              \"param\": [\"CENTER\"]" + "            }" + "          ]"
						+ "        }," + "        {" + "          \"conditions\": ["
						+ "            {" + "              \"name\": \"TrueCondition\","
						+ "              \"param\": [\"\"]" + "            }" + "          ],"
						+ "          \"actions\": [" + "            {"
						+ "              \"name\": \"Turn\","
						+ "              \"param\": [\"LEFT\",\"90\"]" + "            },"
						+ "            {" + "              \"name\": \"MoveInLine\","
						+ "              \"param\": [\"MOVE\",\"FORWARD\",\"100\"]"
						+ "            }," + "            {" + "              \"name\": \"Turn\","
						+ "              \"param\": [\"RIGHT\",\"90\"]" + "            },"
						+ "            {" + "              \"name\": \"MoveInLine\","
						+ "              \"param\": [\"MOVE\",\"FORWARD\",\"100\"]"
						+ "            }" + "          ]" + "        }" + "      ]" + "    },"
						+ "    {" + "      \"teamId\": 1," + "      \"weapons\": ["
						+ "        \"Minigun\"," + "        \"Minigun\","
						+ "        \"MineLauncher\"" + "      ]," + "      \"x\": 250,"
						+ "      \"y\": 50," + "      \"angle\": 0," + "      \"algorithm\": ["
						+ "        {" + "          \"conditions\": [" + "            {"
						+ "              \"name\": \"EnemySpotted\","
						+ "              \"param\": [\"\"]" + "            }" + "          ],"
						+ "          \"actions\": [" + "            {"
						+ "              \"name\": \"FaceOpponent\","
						+ "              \"param\": []" + "            }," + "            {"
						+ "              \"name\": \"Attack\","
						+ "              \"param\": [\"LEFT\"]" + "            },"
						+ "            {" + "              \"name\": \"Attack\","
						+ "              \"param\": [\"RIGHT\"]" + "            }" + "          ]"
						+ "        }," + "        {" + "          \"conditions\": ["
						+ "            {" + "              \"name\": \"WeaponReady\","
						+ "              \"param\": [\"CENTER\"]" + "            }"
						+ "          ]," + "          \"actions\": [" + "            {"
						+ "              \"name\": \"Attack\","
						+ "              \"param\": [\"CENTER\"]" + "            }" + "          ]"
						+ "        }," + "        {" + "          \"conditions\": ["
						+ "            {" + "              \"name\": \"TrueCondition\","
						+ "              \"param\": [\"\"]" + "            }" + "          ],"
						+ "          \"actions\": [" + "            {"
						+ "              \"name\": \"Turn\","
						+ "              \"param\": [\"LEFT\",\"90\"]" + "            },"
						+ "            {" + "              \"name\": \"MoveInLine\","
						+ "              \"param\": [\"MOVE\",\"FORWARD\",\"100\"]"
						+ "            }," + "            {" + "              \"name\": \"Turn\","
						+ "              \"param\": [\"RIGHT\",\"90\"]" + "            },"
						+ "            {" + "              \"name\": \"MoveInLine\","
						+ "              \"param\": [\"MOVE\",\"FORWARD\",\"100\"]"
						+ "            }" + "          ]" + "        }" + "      ]" + "    }"
						+ "  ]" + "}";

		Main.main(new String[] {JSON});
	}
}
