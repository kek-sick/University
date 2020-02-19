from sklearn.preprocessing import OneHotEncoder
import csv


def csv_reader(file_obj):
    reader = csv.reader(file_obj)
    encoder = OneHotEncoder()
    full = []
    for row in reader:
        full.append(row)
    full.pop(0)
    print(full)
    encoded = encoder.fit_transform(full)
    print(encoded)



if __name__ == "__main__":
    csv_path = "data/student-mat.csv"
    with open(csv_path, "r") as f_obj:
        csv_reader(f_obj)
