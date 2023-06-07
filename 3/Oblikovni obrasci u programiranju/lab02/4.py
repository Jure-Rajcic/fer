import random
# Strategy pattern
class NumberGenerationStrategy:
    def generate(self):
        pass


class SequentialGenerationStrategy(NumberGenerationStrategy):
    def __init__(self, start, end, step):
        self.start = start
        self.end = end
        self.step = step

    def generate(self):
        return list(range(self.start, self.end, self.step))


class RandomGenerationStrategy(NumberGenerationStrategy):
    def __init__(self, mean, std_dev, count):
        self.mean = mean
        self.std_dev = std_dev
        self.count = count

    def generate(self):
        return [int(random.gauss(self.mean, self.std_dev)) for _ in range(self.count)]


class FibonacciGenerationStrategy(NumberGenerationStrategy):
    def __init__(self, count):
        self.count = count

    def generate(self):
        sequence = [0, 1]
        for _ in range(self.count - 2):
            sequence.append(sequence[-1] + sequence[-2])
        return sequence


class PercentileStrategy:
    def compute(self, data, percentile):
        pass


class NearestPercentileStrategy(PercentileStrategy):
    def compute(self, data, percentile):
        data.sort()
        index = int(round(percentile * len(data) / 100 + 0.5)) - 1
        return data[index]


class InterpolationPercentileStrategy(PercentileStrategy): # more accurate
    def compute(self, data, percentile):
        data.sort()
        N = len(data)

        if percentile <= 100 * (0.5 / N):
            return data[0]

        if percentile >= 100 * ((N - 0.5) / N):
            return data[-1]

        for i in range(1, N):
            p1 = 100 * (i - 0.5) / N
            p2 = 100 * (i + 0.5) / N

            if p1 <= percentile <= p2:
                return data[i - 1] + N * (percentile - p1) * (data[i] - data[i - 1]) / 100


class DistributionTester:
    def __init__(self, number_generation_strategy, percentile_strategy):
        self.number_generation_strategy = number_generation_strategy
        self.percentile_strategy = percentile_strategy

    def run(self):
        data = self.number_generation_strategy.generate()

        for percentile in range(10, 91, 10):
            value = self.percentile_strategy.compute(data, percentile)
            print(f"{percentile}. percentile: {value}")


def main():

    print("Sequential generation (nearest):")
    DistributionTester(SequentialGenerationStrategy(1, 101, 1), NearestPercentileStrategy()).run()
    print("\nSequential generation (interpolation):")
    DistributionTester(SequentialGenerationStrategy(1, 101, 1), InterpolationPercentileStrategy()).run()


    print("\nRandom generation (nearest):")
    DistributionTester(RandomGenerationStrategy(1, 10, 50), NearestPercentileStrategy()).run()
    print("\nRandom generation (interpolation):")
    DistributionTester(RandomGenerationStrategy(1, 10, 50), InterpolationPercentileStrategy()).run()


    print("\nFibonacci generation (nearest):")
    DistributionTester(FibonacciGenerationStrategy(20), NearestPercentileStrategy()).run()
    print("\nFibonacci generation (interpolation):")
    DistributionTester(FibonacciGenerationStrategy(20), InterpolationPercentileStrategy()).run()

if __name__ == "__main__":
    main()