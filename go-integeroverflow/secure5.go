package main

import "strconv"

func restoreKey(encoded string) (string, error) {
	bits, err := strconv.ParseUint(encoded, 16, 64)
	if err != nil {
		return "", err
	}
	key := int64(bits)
	return strconv.FormatUint(uint64(key), 16), nil
}
