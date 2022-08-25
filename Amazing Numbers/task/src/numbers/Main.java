package numbers;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String greetings = "Welcome to Amazing Numbers!\n\n\n";
        greetings += "Supported requests:\n";
        greetings += "- enter a natural number to know its properties\n";
        greetings += "- enter two natural numbers to obtain the properties of the list:\n";
        greetings += "\t* the first parameter represents a starting number\n";
        greetings += "\t* the second parameter shows how many consecutive numbers are to be processed\n";
        greetings += "- two natural numbers and a property to search for\n";
        greetings += "- two natural numbers and two properties to search for\n";
        greetings += "- a property preceded by minus must not be present in numbers;\n";
        greetings += "- separate the parameters with one space\n";
        greetings += "- enter 0 to exit.\n\n";

        System.out.println(greetings);

        while (true) {
            System.out.print("Enter a request: ");
            String[] inputs = scanner.nextLine().split(" ");

            if ("0".equals(inputs[0])) {
                System.out.println("\nGoodbye!");
                break;
            }

            printNumberInfo(inputs);
        }

        scanner.close();


    }

    private static long[] getNumberIfNatural(String[] inputsFromScanner) {
        double inputToDouble;
        long[] inputToLong = new long[]{0, 0};

        for (int i = 0; i < Math.min(2, inputsFromScanner.length); i++) {
            try {
                inputToDouble = Double.parseDouble(inputsFromScanner[i]);
                inputToLong[i] = Long.parseLong(inputsFromScanner[i]);

                if (inputToLong[i] <= 0 || (double) inputToLong[i] != inputToDouble) {
                    throw new Exception();
                }

            } catch (Exception e) {
                String message = "";
                switch (i) {
                    case 0:
                        message = "The first parameter should be a natural number or zero.";
                        System.out.println(message);
                        break;
                    case 1:
                        message = "The second parameter should be a natural number.\n";
                        System.out.println(message);
                        inputToLong[i] = -1;
                        break;
                    default:
                        break;
                }

            }
        }

        return inputToLong;
    }

    private static Set<String> checkAdditionalInputParams(String[] inputsFromScanner) {

        List<String> wrongNumberTypes = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        Set<String> uniqueInputs = new HashSet<>();

        if (inputsFromScanner.length > 2) {
            String[] subArrayOfInputsFromScanner = Arrays.copyOfRange(inputsFromScanner, 2, inputsFromScanner.length);

            for (String str : subArrayOfInputsFromScanner) {
                if (!"".equals(str)) uniqueInputs.add(str.toUpperCase());
            }

            for (String str : uniqueInputs) {
                if (str.startsWith("-")) {
                    str = str.replace("-", "");
                }
                if (!NumberTypes.containsEntry(str)) {
                    wrongNumberTypes.add(str);
                }
            }

            if (wrongNumberTypes.size() > 0) {
                messages.add(String.format(
                        "The propert%s [%s] %s wrong.\nAvailable properties: [%s]\n",
                        wrongNumberTypes.size() > 1 ? "ies" : "y",
                        String.join(", ", wrongNumberTypes),
                        wrongNumberTypes.size() > 1 ? "are" : "is",
                        NumberTypes.stringify()));
            }

            Set<String> mutuallyExclusive = NumberTypes.checkIfMutuallyExclusive(uniqueInputs);
            if (mutuallyExclusive.size() > 0) {
                messages.add(String.format(
                        "The request contains mutually exclusive properties: [%s].\nThere are no numbers with these properties",
                        String.join(", ", mutuallyExclusive)));
            }

            if (messages.size() == 0) return uniqueInputs;

            messages.forEach(System.out::println);

            return new HashSet<>(List.of("0"));

        }

        return new HashSet<>();

    }

    private static boolean isEven(long inputToLong) {
        return inputToLong % 2 == 0;
    }

    private static Boolean isBuzzNumber(long inputToLong) {
        return inputToLong % 10 == 7 || inputToLong % 7 == 0;
    }

    private static boolean isDuckNumber(long inputToLong) {
        return String.valueOf(inputToLong).contains("0");
    }

    private static boolean isPalindromic(long inputToLong) {
        String inputToString = String.valueOf(inputToLong);

        for (int i = 0; i < inputToString.length(); i++) {
            if (inputToString.charAt(i) != inputToString.charAt(inputToString.length() - i - 1)) return false;
        }

        return true;
    }

    private static boolean isGapful(long inputToLong) {
        String inputToString = String.valueOf(inputToLong);

        if (inputToString.length() < 3) return false;

        long divisor = Long.parseLong("" + inputToString.charAt(0) + "" + inputToString.charAt(inputToString.length() - 1));
        return inputToLong % divisor == 0;
    }

    private static boolean isSpy(long inputToLong) {
        long sum = 0L;
        long product = 1L;
        long num = inputToLong;
        long lastDigit;

        while (num > 0) {
            lastDigit = num % 10;
            sum = sum + lastDigit;
            product = product * lastDigit;
            num = num / 10;
        }

        return sum == product;
    }

    private static boolean isSquare(long inputToLong) {
        double val = Math.sqrt(Math.abs(inputToLong));
        return val - (int) val == 0.0;
    }

    private static boolean isJumping(long inputToLong) {

        while (inputToLong != 0) {
            long digit1 = inputToLong % 10;
            inputToLong = inputToLong / 10;
            if (inputToLong != 0) {
                long digit2 = inputToLong % 10;

                if (Math.abs(digit1 - digit2) != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static long isHappyNumber(long inputToLong) {
        long rem, sum = 0;

        //Calculates the sum of squares of digits
        while (inputToLong > 0) {
            rem = inputToLong % 10;
            sum = sum + (rem * rem);
            inputToLong = inputToLong / 10;
        }

        return sum;
    }

    private static void printNumberInfo(String[] input) {
        long[] inputToLong = getNumberIfNatural(input);
        Set<String> uniqueInputsFromScanner = checkAdditionalInputParams(input);
        String message = "";

        if (inputToLong[0] <= 0) return;
        if (inputToLong[1] < 0) return;
        if (uniqueInputsFromScanner.contains("0")) return;

        if (inputToLong[1] == 0 && input.length == 1) {
            message = NumberTypes.multiLine(inputToLong[0]);
        }

        if (uniqueInputsFromScanner.size() > 0) {
            int counter = 0;
            long currentLong = inputToLong[0];

            do {
                boolean allTypesAreMet = true;

                for (String type : uniqueInputsFromScanner) {
                    boolean negativeTypeBool = type.startsWith("-");
                    if (negativeTypeBool) {
                        String negativeType = type.replace("-", "");
                        allTypesAreMet = !NumberTypes.valueOf(negativeType.toUpperCase())
                                .checkIt(currentLong);
                    } else {
                        allTypesAreMet = NumberTypes.valueOf(type.toUpperCase())
                                .checkIt(currentLong);
                    }

                    if (!allTypesAreMet) break;
                }

                if (allTypesAreMet) {
                    counter++;
                    message += NumberTypes.oneLiner(currentLong) + "\n";
                }
                currentLong++;
            } while (counter < inputToLong[1]);

        }

        if (inputToLong[1] > 0 && inputToLong.length == input.length) {
            for (int i = 0; i < inputToLong[1]; i++) {
                long longNumber = inputToLong[0] + i;
                message += NumberTypes.oneLiner(longNumber) + "\n";
            }
        }


        System.out.println(message);
    }

    private enum NumberTypes {
        EVEN {
            @Override
            public boolean checkIt(long longNumber) {
                return isEven(longNumber);
            }
        },
        ODD {
            @Override
            public boolean checkIt(long longNumber) {
                return !isEven(longNumber);
            }
        },
        BUZZ {
            @Override
            public boolean checkIt(long longNumber) {
                return isBuzzNumber(longNumber);
            }
        },
        DUCK {
            @Override
            public boolean checkIt(long longNumber) {
                return isDuckNumber(longNumber);
            }
        },
        PALINDROMIC {
            @Override
            public boolean checkIt(long longNumber) {
                return isPalindromic(longNumber);
            }
        },
        GAPFUL {
            @Override
            public boolean checkIt(long longNumber) {
                return isGapful(longNumber);
            }
        },
        SPY {
            @Override
            public boolean checkIt(long longNumber) {
                return isSpy(longNumber);
            }
        },
        SQUARE {
            @Override
            public boolean checkIt(long longNumber) {
                return isSquare(longNumber);
            }
        },
        SUNNY {
            @Override
            public boolean checkIt(long longNumber) {
                return isSquare(longNumber + 1);
            }
        },
        JUMPING {
            @Override
            public boolean checkIt(long longNumber) {
                return isJumping(longNumber);
            }
        },
        HAPPY {
            @Override
            public boolean checkIt(long longNumber) {
                long numb = longNumber;
                while (numb != 1 && numb != 4) {
                    numb = isHappyNumber(numb);
                }
                return numb == 1;
            }
        },
        SAD {
            @Override
            public boolean checkIt(long longNumber) {
                long numb = longNumber;
                while (numb != 1 && numb != 4) {
                    numb = isHappyNumber(numb);
                }
                return numb != 1;
            }
        };


        public abstract boolean checkIt(long longNumber);

        public static boolean containsEntry(String entry) {
            try {
                NumberTypes.valueOf(entry.toUpperCase());
            } catch (Exception e) {
                return false;
            }

            return true;
        }

        public static String stringify() {
            return Arrays.stream(NumberTypes.values()).map(String::valueOf).collect(Collectors.joining(", "));
        }

        public static Set<String> checkIfMutuallyExclusive(Set<String> uniqueInputsFromScanner) {
            Set<String> mutuallyExclusive = new HashSet<>();

            Map<String, String> map = new HashMap<>();
            map.put(NumberTypes.EVEN.toString(), NumberTypes.ODD.toString());
            map.put(NumberTypes.ODD.toString(), NumberTypes.EVEN.toString());
            map.put(NumberTypes.SUNNY.toString(), NumberTypes.SQUARE.toString());
            map.put(NumberTypes.SQUARE.toString(), NumberTypes.SUNNY.toString());
            map.put(NumberTypes.DUCK.toString(), NumberTypes.SPY.toString());
            map.put(NumberTypes.SPY.toString(), NumberTypes.DUCK.toString());

            Map<String, String> mapNegative = new HashMap<>();

            mapNegative.put("-" + NumberTypes.EVEN, "-" + NumberTypes.ODD);
            mapNegative.put(NumberTypes.EVEN.toString(), "-" + NumberTypes.ODD);
            mapNegative.put("-"+ NumberTypes.EVEN,NumberTypes.EVEN.toString());

            mapNegative.put("-" + NumberTypes.ODD, "-" + NumberTypes.EVEN);
            mapNegative.put(NumberTypes.ODD.toString(), "-" + NumberTypes.EVEN);
            mapNegative.put(NumberTypes.ODD.toString(), "-" + NumberTypes.ODD);

            mapNegative.put("-" + NumberTypes.SUNNY, "-" + NumberTypes.SQUARE);
            mapNegative.put(NumberTypes.SUNNY.toString(), "-" + NumberTypes.SQUARE);

            mapNegative.put("-" + NumberTypes.SQUARE, "-" + NumberTypes.SUNNY);
            mapNegative.put(NumberTypes.SQUARE.toString(), "-" + NumberTypes.SUNNY);

            mapNegative.put("-" + NumberTypes.DUCK, "-" + NumberTypes.SPY);
            mapNegative.put(NumberTypes.DUCK.toString(), "-" + NumberTypes.SPY);
            mapNegative.put(NumberTypes.DUCK.toString(), "-" + NumberTypes.DUCK);

            mapNegative.put("-" + NumberTypes.SPY, "-" + NumberTypes.DUCK);

            mapNegative.put("-" + NumberTypes.HAPPY, "-" + NumberTypes.SAD);
            mapNegative.put(NumberTypes.HAPPY.toString(), "-" + NumberTypes.SAD);

            mapNegative.put("-" + NumberTypes.SAD, "-" + NumberTypes.HAPPY);
            mapNegative.put(NumberTypes.SAD.toString(), "-" + NumberTypes.HAPPY);


            for (String str : uniqueInputsFromScanner) {
                // if mapNegative contains the key and uniqueInputsFromScanners also contains its mutually exclusive counterpart
                if (map.containsKey(str) && uniqueInputsFromScanner.contains(map.get(str))) {
                    mutuallyExclusive.add(str);
                    mutuallyExclusive.add(map.get(str));

                }
                if (mapNegative.containsKey(str) && uniqueInputsFromScanner.contains(mapNegative.get(str))) {
                    mutuallyExclusive.add(str);
                    mutuallyExclusive.add(mapNegative.get(str));
                }
            }
            return mutuallyExclusive;
        }

        public static String oneLiner(long longNumber) {
            String message = longNumber + " is ";

            for (NumberTypes entry : NumberTypes.values()) {
                message += entry.checkIt(longNumber) ? entry.name().toLowerCase() + " " : "";
            }

            return message.trim();
        }

        public static String multiLine(long longNumber) {
            String message = "Properties of " + longNumber + "\n";

            for (NumberTypes entry : NumberTypes.values()) {
                message += " " + entry.name().toLowerCase() + ": " + entry.checkIt(longNumber) + "\n";
            }

            return message + "\n";
        }
    }

}
