from sklearn.preprocessing import OneHotEncoder
import csv
import numpy


def csv_reader(file_obj):
    reader = csv.reader(file_obj)
    encoder = OneHotEncoder()
    full = []
    for row in reader:
        full.append(row)
    full.pop(0)
    full = numpy.array(full)
    print(full)
    #print(reader.tolist())

    encoded = encoder.fit_transform(full.reshape(-1,1)).toarray()
    path = "output/encoded.csv"
    with open(path, "w", newline='') as csv_file:
        writer = csv.writer(csv_file, delimiter=',')
        for line in encoded:
            writer.writerow(line)


if __name__ == "__main__":
    csv_path = "data/student-mat.csv"
    with open(csv_path, "r") as f_obj:
        csv_reader(f_obj)
