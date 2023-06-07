import time
from abc import ABC, abstractmethod
from typing import List

# strategy
class NumberSource(ABC):
    @abstractmethod
    def get_next_number(self) -> int:
        pass


class KeyboardNumberSource(NumberSource):
    def get_next_number(self) -> int:
        try:
            return int(input("Enter a number (-1 to stop): "))
        except ValueError:
            return -1


class FileNumberSource(NumberSource):
    def __init__(self, file_path):
        self.file = open(file_path, 'r')

    def get_next_number(self) -> int:
        try:
            line = self.file.readline()
            if not line:
                return -1
            return int(line.strip())
        except ValueError:
            return -1
        except IOError:
            return -1


# observer & strategy 
class SequenceObserver(ABC):
    @abstractmethod
    def update(self, data: List[int]) -> None:
        pass


class DisplaySumObserver(SequenceObserver):
    def update(self, data: List[int]) -> None:
        print(f"Sum: {sum(data)}")


class DisplayAverageObserver(SequenceObserver):
    def update(self, data: List[int]) -> None:
        print(f"Average: {sum(data) / len(data)}" if len(data) > 0 else "Average: 0")


class DisplayMedianObserver(SequenceObserver):
    def update(self, data: List[int]) -> None:
        sorted_data = sorted(data)
        length = len(data)
        if length == 0:
            print("Median: 0")
        elif length % 2 == 1:
            print(f"Median: {sorted_data[length // 2]}")
        else:
            print(f"Median: {(sorted_data[length // 2 - 1] + sorted_data[length // 2]) / 2}")


class FileLoggerObserver(SequenceObserver):
    def __init__(self, file_path):
        self.file_path = file_path

    def update(self, data: List[int]) -> None:
        with open(self.file_path, 'a') as file:
            file.write(f"{time.strftime('%Y-%m-%d %H:%M:%S')} - {', '.join(map(str, data))}\n")


# subject
class NumberSequence:
    def __init__(self, number_source: NumberSource):
        self.number_source = number_source
        self.data = []
        self.observers = []

    def attach(self, observer: SequenceObserver):
        self.observers.append(observer)

    def notify_observers(self):
        for observer in self.observers:
            observer.update(self.data)

    def start(self):
        while True:
            number = self.number_source.get_next_number()
            if number == -1:
                break

            self.data.append(number)
            self.notify_observers()


def main():
    number_source = KeyboardNumberSource()
    sequence = NumberSequence(number_source)

    sequence.attach(DisplaySumObserver())
    sequence.attach(DisplayAverageObserver())
    sequence.attach(DisplayMedianObserver())
    sequence.attach(FileLoggerObserver("log.txt"))

    sequence.start()


if __name__ == "__main__":
    main()
